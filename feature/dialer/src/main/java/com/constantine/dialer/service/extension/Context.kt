package com.constantine.dialer.service.extension

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager

inline val Context.telephony: TelephonyManager get() {
    return getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
}

inline val Context.connectivity: ConnectivityManager get() {
    return getSystemService(ConnectivityManager::class.java)
}
