package com.constantine.dialer.service.extension

import android.content.Context
import android.net.ConnectivityManager

inline val Context.connectivity: ConnectivityManager get() {
    return getSystemService(ConnectivityManager::class.java)
}
