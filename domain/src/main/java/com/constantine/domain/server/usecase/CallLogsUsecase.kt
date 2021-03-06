package com.constantine.domain.server.usecase

import androidx.lifecycle.LiveData
import com.constantine.domain.server.parcelable.ContactLog
import com.constantine.domain.server.repository.CallRepository
import javax.inject.Inject

class CallLogsUsecase @Inject constructor(
    private val repository: CallRepository
) {
    suspend fun log(): LiveData<List<ContactLog>> = repository.getLogs()
}
