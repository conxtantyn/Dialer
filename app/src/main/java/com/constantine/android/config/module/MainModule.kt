package com.constantine.android.config.module

import com.constantine.android.MainFeature
import com.constantine.core.content.Feature
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    internal fun provideFeature(feature: MainFeature): Feature = feature
}
