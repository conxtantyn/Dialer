package com.constantine.dialer.ui.screen.home

import androidx.lifecycle.LiveData

class Home {
    internal sealed class State
    internal sealed class Event

    internal interface ViewModel {
        val state: LiveData<State>
        val event: LiveData<Event>
    }
}
