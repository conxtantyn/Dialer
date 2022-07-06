package com.constantine.core.config

import com.constantine.core.DynamicFeature
import com.constantine.core.component.SplitApplication
import com.constantine.core.config.module.CoreModule
import com.constantine.core.config.module.FeatureModule
import com.constantine.core.config.module.InjectionModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        InjectionModule::class,
        CoreModule::class,
        FeatureModule::class
    ]
)
interface CoreComponent : AndroidInjector<SplitApplication>, DynamicFeature.Component {
    fun features(): List<DynamicFeature>

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: SplitApplication): Builder

        fun build(): CoreComponent
    }
}
