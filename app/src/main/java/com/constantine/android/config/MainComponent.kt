package com.constantine.android.config

import com.constantine.android.MainApplication
import com.constantine.android.config.module.MainModule
import com.constantine.core.config.scope.Featured
import com.constantine.core.content.Feature
import dagger.Component
import dagger.android.AndroidInjector

@Featured
@Component(
    modules = [MainModule::class],
    dependencies = [Feature.Component::class]
)
internal interface MainComponent : AndroidInjector<MainApplication> {
    fun feature(): Feature
}
