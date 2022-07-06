package com.constantine.android

import com.constantine.android.config.DaggerMainComponent
import com.constantine.core.DynamicFeature
import com.constantine.core.config.scope.Feature
import javax.inject.Inject

@Feature
class MainFeature @Inject constructor() : DynamicFeature {
    override fun onCreate() {}

    class Provider : DynamicFeature.Provider {
        override fun get(component: DynamicFeature.Component): DynamicFeature {
            val app = component.application() as MainApplication
            return DaggerMainComponent
                .builder()
                .component(component)
                .build().apply {
                    inject(app)
                }.feature()
        }
    }
}
