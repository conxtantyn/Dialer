package com.constantine.core.config.module

import com.constantine.core.content.SimpleAndroidInjector
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector

@Module
class InjectionModule {
    @Provides
    fun classKeyedInjectorFactories(injector: SimpleAndroidInjector): Map<Class<*>,
        @JvmSuppressWildcards javax.inject.Provider<AndroidInjector.Factory<*>>> {
        return injector.classKeyedInjectorFactories
    }

    @Provides
    fun stringKeyedInjectorFactories(injector: SimpleAndroidInjector): Map<String,
        @JvmSuppressWildcards javax.inject.Provider<AndroidInjector.Factory<*>>> {
        return injector.stringKeyedInjectorFactories
    }
}
