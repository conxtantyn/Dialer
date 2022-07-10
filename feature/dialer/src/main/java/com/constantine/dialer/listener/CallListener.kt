package com.constantine.dialer.listener

import android.os.Build
import android.telephony.PhoneStateListener
import android.telephony.TelephonyCallback
import androidx.annotation.RequiresApi
import java.util.WeakHashMap

interface CallListener {
    fun onCallStateChanged(state: Int)

    @RequiresApi(Build.VERSION_CODES.S)
    class Telephony(listener: CallListener) :
        TelephonyCallback(),
        TelephonyCallback.CallStateListener {
        private val listenerMap = WeakHashMap<CallListener, String>()

        init {
            listenerMap[listener] = listener.javaClass.name
        }

        override fun onCallStateChanged(state: Int) {
            System.gc()
            listenerMap.keys.forEach { it.onCallStateChanged(state) }
        }
    }

    class PhoneState(listener: CallListener) : PhoneStateListener() {
        private val listenerMap = WeakHashMap<CallListener, String>()

        init {
            listenerMap[listener] = listener.javaClass.name
        }

        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            System.gc()
            listenerMap.keys.forEach { it.onCallStateChanged(state) }
            super.onCallStateChanged(state, phoneNumber)
        }
    }
}
