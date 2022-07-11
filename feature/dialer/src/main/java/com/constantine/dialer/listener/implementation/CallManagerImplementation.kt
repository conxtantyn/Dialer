package com.constantine.dialer.listener.implementation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.TelephonyManager
import android.telephony.TelephonyManager.CALL_STATE_IDLE
import android.telephony.TelephonyManager.CALL_STATE_OFFHOOK
import android.telephony.TelephonyManager.EXTRA_STATE_IDLE
import android.telephony.TelephonyManager.EXTRA_STATE_OFFHOOK
import com.constantine.dialer.listener.CallManager
import java.util.WeakHashMap

class CallManagerImplementation : BroadcastReceiver(), CallManager {

    private val listenerMap = WeakHashMap<CallManager.CallListener, String>()

    private val callStateMap = mapOf(
        Pair(EXTRA_STATE_IDLE, CALL_STATE_IDLE),
        Pair(EXTRA_STATE_OFFHOOK, CALL_STATE_OFFHOOK)
    )

    override fun registerService(
        context: Context,
        listener: CallManager.CallListener
    ) {
        this.listenerMap[listener] = listener.javaClass.name
        context.registerReceiver(
            this,
            IntentFilter().also {
                it.addAction("android.intent.action.PHONE_STATE")
                it.addAction("android.intent.action.NEW_OUTGOING_CALL")
            }
        )
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val extraState = it.getStringExtra(TelephonyManager.EXTRA_STATE)
            val extraNumber = it.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            System.gc()
            listenerMap.keys.forEach { listener ->
                callStateMap[extraState]?.let { state ->
                    extraNumber?.let { number ->
                        listener.onCallStateChanged(state, number)
                    }
                }
            }
        }
    }

    override fun unRegisterService(context: Context) = context.unregisterReceiver(this)
}
