package com.constantine.dialer.usecase

import android.content.Context
import android.content.ServiceConnection
import com.constantine.dialer.repository.DialerServiceRepository
import javax.inject.Inject

class BindServiceUsecase @Inject constructor(
    private val repository: DialerServiceRepository
) {
    fun bind(context: Context, connection: ServiceConnection) {
        repository.bind(context, connection)
    }
}
