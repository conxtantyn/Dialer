package com.constantine.dialer.listener

import android.content.Context
import android.os.Build
import android.telephony.PhoneStateListener
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import java.util.WeakHashMap

interface CallListener {
    fun onCallStateChanged(state: Int)

    interface CallState

    interface CallService {
        fun registerService(
            service: Context,
            telephony: TelephonyManager,
            listener: CallListener
        )
        fun unRegisterService()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    class Telephony(listener: CallListener) :
        TelephonyCallback(),
        TelephonyCallback.CallStateListener,
        CallState {
        private val listenerMap = WeakHashMap<CallListener, String>()

        init {
            listenerMap[listener] = listener.javaClass.name
        }

        override fun onCallStateChanged(state: Int) {
            System.gc()
            listenerMap.keys.forEach { it.onCallStateChanged(state) }
        }
    }

    class PhoneState(listener: CallListener) : PhoneStateListener(), CallState {
        private val listenerMap = WeakHashMap<CallListener, String>()

        init {
            listenerMap[listener] = listener.javaClass.name
        }

        @Deprecated("Deprecated in Java")
        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            System.gc()
            listenerMap.keys.forEach { it.onCallStateChanged(state) }
            super.onCallStateChanged(state, phoneNumber)
        }
    }
}
