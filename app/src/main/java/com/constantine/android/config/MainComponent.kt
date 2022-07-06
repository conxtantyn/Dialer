package com.constantine.android.config

import com.constantine.android.config.module.MainModule
import com.constantine.android.config.module.UIModule
import com.constantine.core.DynamicFeature
import com.constantine.core.config.scope.Feature
import dagger.Component
import dagger.android.AndroidInjectionModule

@Feature
@Component(
    modules = [
        AndroidInjectionModule::class,
        MainModule::class,
        UIModule::class
    ],
    dependencies = [DynamicFeature.Component::class]
)
internal interface MainComponent {
    fun feature(): DynamicFeature
}
