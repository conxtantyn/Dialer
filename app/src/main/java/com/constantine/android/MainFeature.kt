package com.constantine.android

import com.constantine.android.config.DaggerMainComponent
import com.constantine.core.config.scope.Featured
import com.constantine.core.content.Feature
import javax.inject.Inject

@Featured
class MainFeature @Inject constructor() : Feature {
    override fun onCreate() {}

    class Provider : Feature.Provider {
        override fun get(component: Feature.Component): Feature {
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
