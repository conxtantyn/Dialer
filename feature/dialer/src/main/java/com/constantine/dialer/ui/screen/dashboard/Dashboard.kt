package com.constantine.dialer.ui.screen.dashboard

import androidx.lifecycle.LiveData

class Dashboard {
    internal sealed class State
    internal sealed class Event
    internal interface ViewModel {
        val state: LiveData<State>
        val event: LiveData<Event>
    }
}
