package com.constantine.android.ui.screen.main

import androidx.lifecycle.LiveData

class Main {
    internal sealed class State
    internal sealed class Event
    internal interface ViewModel {
        val state: LiveData<State>
        val event: LiveData<Event>
    }
}
