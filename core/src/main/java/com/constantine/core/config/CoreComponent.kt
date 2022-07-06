package com.constantine.core.config

import android.app.Application
import com.constantine.core.config.module.CoreModule
import com.constantine.core.config.module.FeatureModule
import com.constantine.core.content.Feature
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        CoreModule::class,
        FeatureModule::class
    ]
)
interface CoreComponent : Feature.Component {
    fun features(): List<Feature>

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): CoreComponent
    }
}
