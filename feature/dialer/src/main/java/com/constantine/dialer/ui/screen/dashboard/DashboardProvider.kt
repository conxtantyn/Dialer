package com.constantine.dialer.ui.screen.dashboard

import com.constantine.dialer.config.scope.Dialer
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DashboardProvider {
    @Dialer
    @ContributesAndroidInjector(modules = [DashboardModule::class])
    internal abstract fun provideDashboardFragment(): DashboardFragment
}
