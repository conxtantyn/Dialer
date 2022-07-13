package com.constantine.dialer

import com.constantine.core.component.SplitApplication
import com.constantine.core.config.DaggerCoreComponent
import com.constantine.data.config.module.DataModule
import com.constantine.dialer.config.DaggerDataTestComponent
import com.constantine.dialer.config.DaggerDialerTestComponent
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector

class DialerApplication : SplitApplication(), HasAndroidInjector {
    override fun onCreate() {
        super.onCreate()
        initDaggerAppComponent()
    }

    private fun initDaggerAppComponent() {
        val dataComponent = DaggerDataTestComponent
            .builder()
            .dataModule(DataModule(this))
            .build()
        val component = DaggerCoreComponent
            .builder()
            .application(this)
            .build()

        val dialerTestComponent = DaggerDialerTestComponent
            .builder()
            .component(component)
            .domainComponent(dataComponent)
            .build()

        dialerTestComponent.inject(this)

        dialerTestComponent.feature().onCreate()
    }

    override fun androidInjector(): AndroidInjector<Any> = injector
}
