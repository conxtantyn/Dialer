package com.constantine.core

import android.app.Application
import android.content.Context

interface DynamicFeature {
    fun onCreate()

    interface Provider {
        fun get(component: Component): DynamicFeature
    }

    interface Component {
        fun context(): Context

        fun application(): Application
    }
}
