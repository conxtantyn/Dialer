package com.constantine.domain.server.usecase

import androidx.lifecycle.LiveData
import com.constantine.domain.parcelable.ContactLog
import com.constantine.domain.usecase.InstallTimestampUsecase
import javax.inject.Inject

class GetCallLogsUsecase @Inject constructor(
    private val callLogsUsecase: CallLogsUsecase,
    private val installTimestampUsecase: InstallTimestampUsecase
) {
    suspend fun log(): LiveData<List<ContactLog>> {
        return callLogsUsecase.log(installTimestampUsecase.timestamp())
    }
}
