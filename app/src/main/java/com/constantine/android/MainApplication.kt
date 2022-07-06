package com.constantine.android

import com.constantine.core.component.SplitApplication
import com.constantine.core.config.DaggerCoreComponent
import com.constantine.data.config.DaggerDataComponent
import com.constantine.domain.config.DomainComponent
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber

class MainApplication : SplitApplication(), DomainComponent.Injector, HasAndroidInjector {

    private lateinit var dataComponent: DomainComponent

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

    private fun initDaggerAppComponent() {
        dataComponent = DaggerDataComponent.builder().build()
        val component = DaggerCoreComponent
            .builder()
            .application(this)
            .build()

        component.features().forEach { it.onCreate() }
        component.inject(this)
    }

    override val domain: DomainComponent get() = dataComponent

    override fun androidInjector(): AndroidInjector<Any> = injector
}
