package com.constantine.dialer.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.constantine.core.component.SingleLiveEvent
import javax.inject.Inject

internal class HomeViewModel @Inject constructor() : ViewModel(), Home.ViewModel {
    private val mutableState = MutableLiveData<Home.State>()

    private val mutableEvent = SingleLiveEvent<Home.Event>()

    override val state: LiveData<Home.State> get() = mutableState

    override val event: LiveData<Home.Event> get() = mutableEvent
}
