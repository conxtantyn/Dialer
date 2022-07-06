package com.constantine.android.ui.screen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.constantine.core.component.SingleLiveEvent
import javax.inject.Inject

internal class MainViewModel @Inject constructor() : ViewModel(), Main.ViewModel {
    private val mutableState = MutableLiveData<Main.State>()

    private val mutableEvent = SingleLiveEvent<Main.Event>()

    override val state: LiveData<Main.State> get() = mutableState

    override val event: LiveData<Main.Event> get() = mutableEvent
}
