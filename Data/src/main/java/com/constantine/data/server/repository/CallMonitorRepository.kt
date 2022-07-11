package com.constantine.data.server.repository

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.telephony.TelephonyManager
import com.constantine.domain.parcelable.Contact
import com.constantine.domain.server.repository.CallRepository
import javax.inject.Inject

class CallMonitorRepository @Inject constructor(
    private val context: Context
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
}
