package com.constantine.dialer.config.module

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.constantine.core.DynamicFeature
import com.constantine.core.config.scope.Feature
import com.constantine.dialer.DialerFeature
import com.constantine.dialer.R
import dagger.Module
import dagger.Provides

@Module
class DialerModule {
    @Feature
    @Provides
    internal fun provideUri(context: Context): Uri {
        val host = context.getString(R.string.host)
        val port = context.resources.getInteger(R.integer.port)

        return "$host:$port".toUri()
    }

    @Provides
    internal fun provideFeature(feature: DialerFeature): DynamicFeature = feature
}
