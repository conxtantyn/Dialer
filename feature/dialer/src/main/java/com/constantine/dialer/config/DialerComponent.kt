package com.constantine.dialer.config

import com.constantine.core.config.scope.Featured
import com.constantine.core.content.Feature
import com.constantine.dialer.config.module.DialerModule
import dagger.Component

@Featured
@Component(
    modules = [DialerModule::class],
    dependencies = [Feature.Component::class]
)
internal interface DialerComponent {
    fun feature(): Feature
}
