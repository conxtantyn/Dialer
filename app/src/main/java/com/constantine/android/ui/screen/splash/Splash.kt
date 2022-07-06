package com.constantine.android.ui.screen.splash

import androidx.lifecycle.LiveData

class Splash {
    internal sealed class State {
        object Loading : State()
        object Initialized : State()
    }
    internal sealed class Event {
        object AnimationEnd : Event()
    }

    internal interface ViewModel {
        val state: LiveData<State>
        val event: LiveData<Event>

        fun animate(duration: Long)

        fun initialize()
    }
}
