package com.constantine.dialer.config.module

import com.constantine.dialer.service.DialerService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ServiceModule {
    @ContributesAndroidInjector
    abstract fun bindDialerService(): DialerService
}
