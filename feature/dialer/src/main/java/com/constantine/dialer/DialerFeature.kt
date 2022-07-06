package com.constantine.dialer

import com.constantine.core.DynamicFeature
import com.constantine.core.config.scope.Feature
import com.constantine.core.content.InjectorProvider
import com.constantine.dialer.config.DaggerDialerComponent
import com.constantine.domain.config.DomainComponent
import javax.inject.Inject

@Feature
class DialerFeature @Inject constructor(
    private val injectorProvider: InjectorProvider
) : DynamicFeature {
    override fun onCreate() {
        injectorProvider.provide()
    }

    class Provider : DynamicFeature.Provider {
        override fun get(component: DynamicFeature.Component): DynamicFeature {
            val app = component.application() as DomainComponent.Injector
            return DaggerDialerComponent
                .builder()
                .component(component)
                .domainComponent(app.domain)
                .build()
                .feature()
        }
    }
}
