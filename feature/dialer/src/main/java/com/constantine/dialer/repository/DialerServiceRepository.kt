package com.constantine.dialer.repository

import android.content.Context
import android.content.ServiceConnection
import com.constantine.dialer.service.DialerService
import javax.inject.Inject

class DialerServiceRepository @Inject constructor() {
    fun isRunning(): Boolean = DialerService.IS_SERVICE_RUNNING

    fun start(context: Context) = DialerService.startService(context)

    fun bind(context: Context, connection: ServiceConnection) {
        DialerService.bindService(context, connection)
    }
}
