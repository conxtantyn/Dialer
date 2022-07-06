package com.constantine.dialer.ui.screen.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.constantine.core.component.SingleLiveEvent
import javax.inject.Inject

internal class DashboardViewModel @Inject constructor() : ViewModel(), Dashboard.ViewModel {
    private val mutableState = MutableLiveData<Dashboard.State>()

    private val mutableEvent = SingleLiveEvent<Dashboard.Event>()

    override val state: LiveData<Dashboard.State> get() = mutableState

    override val event: LiveData<Dashboard.Event> get() = mutableEvent
}
