package com.constantine.dialer.config

import com.constantine.core.DynamicFeature
import com.constantine.core.config.scope.Feature
import com.constantine.dialer.config.module.DialerModule
import com.constantine.domain.config.DomainComponent
import dagger.Component
import dagger.android.AndroidInjectionModule

@Feature
@Component(
    modules = [
        AndroidInjectionModule::class,
        DialerModule::class
    ],
    dependencies = [DynamicFeature.Component::class, DomainComponent::class]
)
internal interface DialerComponent {
    fun feature(): DynamicFeature
}
