package com.constantine.dialer.config.module

import com.constantine.core.DynamicFeature
import com.constantine.dialer.DialerFeature
import dagger.Module
import dagger.Provides

@Module
class DialerModule {
    @Provides
    internal fun provideFeature(feature: DialerFeature): DynamicFeature = feature
}
