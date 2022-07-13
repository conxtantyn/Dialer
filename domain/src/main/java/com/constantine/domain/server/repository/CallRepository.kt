package com.constantine.domain.server.repository

import androidx.lifecycle.LiveData
import com.constantine.domain.parcelable.Contact
import com.constantine.domain.parcelable.ContactLog

interface CallRepository {
    fun getStatus(): Contact?

    fun onStateChanged(state: Int, number: String)

    suspend fun getLogs(): LiveData<List<ContactLog>>

    suspend fun getLogList(): List<ContactLog>
}
