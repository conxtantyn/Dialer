package com.constantine.core.content

import com.constantine.core.component.SplitApplication
import dagger.android.AndroidInjector
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SimpleAndroidInjector @Inject constructor(
    private val application: SplitApplication
) {
    private val componentInjectorFactoriesWithClassKeys = mutableMapOf<Class<*>,
        @JvmSuppressWildcards javax.inject.Provider<AndroidInjector.Factory<*>>>()

    private val componentInjectorFactoriesWithStringKeys = mutableMapOf<String,
        @JvmSuppressWildcards javax.inject.Provider<AndroidInjector.Factory<*>>>()

    val classKeyedInjectorFactories get() = componentInjectorFactoriesWithClassKeys

    val stringKeyedInjectorFactories get() = componentInjectorFactoriesWithStringKeys

    val injector get() = application.injector

    fun maybeInject(instance: Any): Boolean {
        return classKeyedInjectorFactories.contains(instance.javaClass)
    }

    fun inject(
        injectorFactoriesWithClassKeys: Map<Class<*>,
            @JvmSuppressWildcards javax.inject.Provider<AndroidInjector.Factory<*>>>,
        injectorFactoriesWithStringKeys: Map<String,
            @JvmSuppressWildcards javax.inject.Provider<AndroidInjector.Factory<*>>>
    ) {
        componentInjectorFactoriesWithClassKeys.putAll(injectorFactoriesWithClassKeys)
        componentInjectorFactoriesWithStringKeys.putAll(injectorFactoriesWithStringKeys)
    }
}
