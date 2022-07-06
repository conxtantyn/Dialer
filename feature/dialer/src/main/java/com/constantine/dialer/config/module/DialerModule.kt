package com.constantine.dialer.config.module

import com.constantine.core.content.Feature
import com.constantine.dialer.DialerFeature
import dagger.Module
import dagger.Provides

@Module
class DialerModule {
    @Provides
    internal fun provideFeature(feature: DialerFeature): Feature = feature
}
