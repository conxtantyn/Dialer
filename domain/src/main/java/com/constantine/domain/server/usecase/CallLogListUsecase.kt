package com.constantine.domain.server.usecase

import com.constantine.domain.parcelable.ContactLog
import com.constantine.domain.server.repository.CallRepository
import javax.inject.Inject

class CallLogListUsecase @Inject constructor(
    private val repository: CallRepository
) {
    suspend fun log(timestamp: Long): List<ContactLog> = repository.getLogList(timestamp)
}
