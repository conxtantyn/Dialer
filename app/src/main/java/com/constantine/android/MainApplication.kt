package com.constantine.android

import com.constantine.core.config.DaggerCoreComponent
import com.constantine.data.config.DaggerDataComponent
import com.constantine.domain.config.DomainComponent
import com.google.android.play.core.splitcompat.SplitCompatApplication
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class MainApplication : SplitCompatApplication(), DomainComponent.Injector, HasAndroidInjector {

    @Inject
    internal lateinit var injector: DispatchingAndroidInjector<Any>

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
        DaggerCoreComponent
            .builder()
            .application(this)
            .build()
            .features()
            .forEach { it.onCreate() }
    }

    override val domain: DomainComponent get() = dataComponent

    override fun androidInjector(): AndroidInjector<Any> = injector
}
