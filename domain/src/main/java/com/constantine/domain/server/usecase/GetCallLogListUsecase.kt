package com.constantine.domain.server.usecase

import com.constantine.domain.parcelable.ContactLog
import com.constantine.domain.usecase.InstallTimestampUsecase
import javax.inject.Inject

class GetCallLogListUsecase @Inject constructor(
    private val callLogsListUsecase: CallLogListUsecase,
    private val installTimestampUsecase: InstallTimestampUsecase
) {
    suspend fun log(): List<ContactLog> {
        return callLogsListUsecase.log(installTimestampUsecase.timestamp())
    }
}
