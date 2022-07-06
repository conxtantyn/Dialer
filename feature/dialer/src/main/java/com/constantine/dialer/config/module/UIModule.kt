package com.constantine.dialer.config.module

import com.constantine.dialer.ui.screen.home.HomeProvider
import dagger.Module

@Module(
    includes = [
        HomeProvider::class
    ]
)
class UIModule
