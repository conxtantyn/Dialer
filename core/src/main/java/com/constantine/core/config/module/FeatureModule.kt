package com.constantine.core.config.module

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import com.constantine.core.config.CoreComponent
import com.constantine.core.content.Feature
import dagger.Module
import dagger.Provides
import timber.log.Timber

@Module
object FeatureModule {
    private val features: MutableMap<Class<*>, Feature> = mutableMapOf()

    @Provides
    @JvmStatic
    fun provideFeatures(context: Context, component: CoreComponent): List<Feature> {
        val domain = context.packageName
        val bundle = context.packageManager.getApplicationInfo(
            domain, PackageManager.GET_META_DATA
        ).metaData
        // provide main app feature first
        loadFeature(domain, bundle, component)
        bundle.keySet().forEach {
            try {
                if (it != domain) loadFeature(it, bundle, component)
            } catch (e: ClassNotFoundException) { Timber.e(e) }
        }
        return features.values.toList()
    }

    private fun loadFeature(key: String, bundle: Bundle, component: CoreComponent) {
        if (key.indexOf(component.context().packageName) == 0) {
            bundle.getString(key)?.let { provider ->
                val klazz = Class.forName(provider)
                if (features[klazz] == null) {
                    (klazz.getDeclaredConstructor().newInstance() as? Feature.Provider?)
                        ?.get(component)?.also {
                            features[klazz] = it
                        }
                }
            }
        }
    }
}
