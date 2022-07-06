package com.constantine.android

import com.constantine.core.config.DaggerCoreComponent
import com.google.android.play.core.splitcompat.SplitCompatApplication
import timber.log.Timber

class MainApplication : SplitCompatApplication() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initDaggerAppComponent()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    protected open fun initDaggerAppComponent() {
        DaggerCoreComponent
            .builder()
            .application(this)
            .build()
            .features()
            .forEach { it.onCreate() }
    }
}
