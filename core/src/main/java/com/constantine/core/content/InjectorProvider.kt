package com.constantine.core.content

import dagger.android.AndroidInjector
import javax.inject.Inject

class InjectorProvider @Inject constructor(
    private val injectorManager: SimpleAndroidInjector,
    private val injectorFactoriesWithClassKeys: Map<Class<*>,
        @JvmSuppressWildcards javax.inject.Provider<
            AndroidInjector.Factory<*>>>,
    private val injectorFactoriesWithStringKeys: Map<String,
        @JvmSuppressWildcards javax.inject.Provider<
            AndroidInjector.Factory<*>>>
) {
    fun provide() = injectorManager.inject(
        injectorFactoriesWithClassKeys,
        injectorFactoriesWithStringKeys
    )
}
