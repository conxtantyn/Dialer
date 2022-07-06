package com.constantine.android

import com.constantine.android.config.DaggerMainComponent
import com.constantine.core.DynamicFeature
import com.constantine.core.config.scope.Feature
import com.constantine.core.content.InjectorProvider
import javax.inject.Inject

@Feature
class MainFeature @Inject constructor(
    private val injectorProvider: InjectorProvider
) : DynamicFeature {
    override fun onCreate() {
        injectorProvider.provide()
    }

    class Provider : DynamicFeature.Provider {
        override fun get(component: DynamicFeature.Component): DynamicFeature {
            return DaggerMainComponent
                .builder()
                .component(component)
                .build().feature()
        }
    }
}
