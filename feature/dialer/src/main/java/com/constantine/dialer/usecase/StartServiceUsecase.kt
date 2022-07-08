package com.constantine.dialer.usecase

import android.content.Context
import com.constantine.dialer.repository.DialerServiceRepository
import javax.inject.Inject

class StartServiceUsecase @Inject constructor(
    private val repository: DialerServiceRepository
) {
    fun start(context: Context) = repository.start(context)
}
