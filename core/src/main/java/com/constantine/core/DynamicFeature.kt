package com.constantine.core

import android.content.Context
import com.constantine.core.component.SplitApplication
import com.constantine.core.content.SimpleAndroidInjector

interface DynamicFeature {
    fun onCreate()

    interface Provider {
        fun get(component: Component): DynamicFeature
    }

    interface Component {
        fun context(): Context

        fun application(): SplitApplication

        fun injector(): SimpleAndroidInjector
    }
}
