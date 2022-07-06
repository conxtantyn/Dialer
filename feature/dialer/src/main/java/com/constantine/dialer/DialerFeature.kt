package com.constantine.dialer

import com.constantine.core.config.scope.Featured
import com.constantine.core.content.Feature
import com.constantine.dialer.config.DaggerDialerComponent
import javax.inject.Inject

@Featured
class DialerFeature @Inject constructor() : Feature {
    override fun onCreate() {}

    class Provider : Feature.Provider {
        override fun get(component: Feature.Component): Feature {
            return DaggerDialerComponent.builder().component(component).build().feature()
        }
    }
}
