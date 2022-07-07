package com.constantine.dialer.usecase

import com.constantine.dialer.service.DialerService
import javax.inject.Inject

class ServiceStateUsecase @Inject constructor() {
    fun get(): Boolean = DialerService.IS_SERVICE_RUNNING
}
