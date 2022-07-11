package com.constantine.dialer.listener

import android.content.Context

interface CallManager {
    fun registerService(
        context: Context,
        listener: CallListener
    )

    fun unRegisterService(context: Context)

    interface CallListener {
        fun onCallStateChanged(state: Int, number: String)
    }
}
