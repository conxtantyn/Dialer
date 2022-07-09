package com.constantine.dialer.util

import java.net.NetworkInterface
import java.util.Collections

fun getIPAddress(): String? {
    Collections.list(NetworkInterface.getNetworkInterfaces()).forEach {
        Collections.list(it.inetAddresses).forEach { address ->
            if (!address.isLoopbackAddress) {
                if ((address.hostAddress?.indexOf(':') ?: 0) < 0) {
                    return address.hostAddress
                }
            }
        }
    }
    return null
}
