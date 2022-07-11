package com.constantine.domain.usecase

import com.constantine.domain.repository.ConfigRepository
import javax.inject.Inject

class BootstrapUsecase @Inject constructor(
    private val configRepository: ConfigRepository
) {
    fun init() = configRepository.initialize()
}
