package com.constantine.dialer.usecase

import com.constantine.dialer.repository.DialerServiceRepository
import javax.inject.Inject

class ServiceStateUsecase @Inject constructor(
    private val repository: DialerServiceRepository
) {
    fun get(): Boolean = repository.isRunning()
}
