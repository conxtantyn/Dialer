package com.constantine.android.config.module

import com.constantine.android.ui.screen.main.MainProvider
import dagger.Module

@Module(
    includes = [
        MainProvider::class
    ]
)
class UIModule
