package com.constantine.dialer.ui.screen.listing

import androidx.lifecycle.LiveData

class Listing {
    internal sealed class State
    internal sealed class Event
    internal interface ViewModel {
        val state: LiveData<State>
        val event: LiveData<Event>
    }
}
