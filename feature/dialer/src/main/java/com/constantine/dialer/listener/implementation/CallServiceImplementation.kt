package com.constantine.dialer.listener.implementation

import android.content.Context
import android.os.Build
import android.telephony.PhoneStateListener
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import com.constantine.dialer.listener.CallListener

class CallServiceImplementation : CallListener.CallService {

    private lateinit var telephony: TelephonyManager

    private lateinit var callState: CallListener.CallState

    override fun registerService(
        service: Context,
        telephony: TelephonyManager,
        listener: CallListener
    ) {
        this.telephony = telephony
        this.callState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            CallListener.Telephony(listener)
        } else {
            CallListener.PhoneState(listener)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            telephony.registerTelephonyCallback(
                service.mainExecutor,
                callState as TelephonyCallback
            )
        } else {
            telephony.listen(callState as PhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
        }
    }

    override fun unRegisterService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            telephony.unregisterTelephonyCallback(callState as TelephonyCallback)
        } else {
            telephony.listen(callState as PhoneStateListener, PhoneStateListener.LISTEN_NONE)
        }
    }
}
