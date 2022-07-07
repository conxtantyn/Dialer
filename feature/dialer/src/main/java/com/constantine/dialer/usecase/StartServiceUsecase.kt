package com.constantine.dialer.usecase

import android.content.Context
import com.constantine.dialer.service.DialerService
import javax.inject.Inject

class StartServiceUsecase @Inject constructor() {
    fun start(context: Context) = DialerService.startService(context)
}
