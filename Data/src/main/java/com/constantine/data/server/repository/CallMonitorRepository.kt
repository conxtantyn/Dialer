package com.constantine.data.server.repository

import android.content.Context
import android.net.Uri
import android.provider.CallLog
import android.provider.ContactsContract
import android.telephony.TelephonyManager
import androidx.lifecycle.LiveData
import com.constantine.data.server.data.dao.CallLogDao
import com.constantine.data.server.entity.ContactLogEntity
import com.constantine.domain.parcelable.Contact
import com.constantine.domain.parcelable.ContactLog
import com.constantine.domain.server.repository.CallRepository
import javax.inject.Inject

class CallMonitorRepository @Inject constructor(
    private val context: Context,
    private val callLogDao: CallLogDao
) : CallRepository {

    private var activeCaller: Contact? = null

    override fun getStatus(): Contact? = activeCaller

    override fun onStateChanged(state: Int, number: String) {
        when (state) {
            TelephonyManager.CALL_STATE_IDLE -> activeCaller = null
            TelephonyManager.CALL_STATE_OFFHOOK -> {
                activeCaller = getContact(number)
            }
        }
    }

    private fun getContact(number: String): Contact {
        val uri = Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
            Uri.encode(number)
        )
        val cursor = context.contentResolver.query(
            uri,
            arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME),
            null,
            null,
            null
        )?.also {
            val callNumber = it.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)
            if (it.moveToFirst()) {
                return Contact(it.getString(callNumber), number)
            }
        }
        cursor?.close()
        return Contact(null, number)
    }

    override suspend fun getLogList(timestamp: Long): List<ContactLog> {
        refreshLog(timestamp)
        return callLogDao.getList()
    }

    override suspend fun getLogs(timestamp: Long): LiveData<List<ContactLog>> {
        refreshLog(timestamp)
        return callLogDao.getAll()
    }

    private suspend fun refreshLog(timestamp: Long) {
        val logs = mutableListOf<ContactLogEntity>()
        val selection = "${CallLog.Calls.DATE} >= ?"
        val selectionArgs = arrayOf(timestamp.toString())
        val cursor = context.contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            null,
            selection,
            selectionArgs,
            null
        )?.also {
            val idIndex = it.getColumnIndex(ContactsContract.Contacts._ID)
            val dateIndex = it.getColumnIndex(CallLog.Calls.DATE)
            val nameIndex = it.getColumnIndex(CallLog.Calls.CACHED_NAME)
            val numberIndex = it.getColumnIndex(CallLog.Calls.NUMBER)
            val durationIndex = it.getColumnIndex(CallLog.Calls.DURATION)
            while (it.moveToNext()) {
                logs.add(
                    ContactLogEntity(
                        id = null,
                        uId = it.getLong(idIndex).toString(),
                        name = it.getString(nameIndex),
                        number = it.getString(numberIndex),
                        duration = it.getLong(durationIndex),
                        date = it.getLong(dateIndex)
                    )
                )
            }
        }
        cursor?.close()
        callLogDao.insert(logs)
    }
}
