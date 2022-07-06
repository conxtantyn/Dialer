package com.constantine.core.content

import android.app.Application
import android.content.Context

interface Feature {
    fun onCreate()

    interface Provider {
        fun get(component: Component): Feature
    }

    interface Component {
        fun context(): Context

        fun application(): Application
    }
}
