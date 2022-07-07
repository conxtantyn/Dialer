package com.constantine.dialer.ui.screen.dashboard.extension

import android.os.RemoteException
import com.constantine.dialer.ui.screen.dashboard.DashboardFragment

fun DashboardFragment.handleAction(action: () -> Unit) = try {
    action()
} catch (ex: RemoteException) {
    updateControl()
}
