package com.constantine.dialer.usecase

import android.content.Context
import android.content.ServiceConnection
import com.constantine.dialer.service.DialerService
import javax.inject.Inject

class BindServiceUsecase @Inject constructor() {
    fun bind(context: Context, connection: ServiceConnection) {
        DialerService.bindService(context, connection)
    }
}
