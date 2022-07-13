package com.constantine.dialer

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class DialerRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, DialerApplication::class.java.name, context)
    }
}
