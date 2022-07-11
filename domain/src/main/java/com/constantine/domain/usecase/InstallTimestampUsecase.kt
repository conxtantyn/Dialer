package com.constantine.domain.usecase

import com.constantine.domain.repository.ConfigRepository
import javax.inject.Inject

class InstallTimestampUsecase @Inject constructor(
    private val configRepository: ConfigRepository
) {
    fun timestamp(): Long = configRepository.getInstallTimestamp()
}
