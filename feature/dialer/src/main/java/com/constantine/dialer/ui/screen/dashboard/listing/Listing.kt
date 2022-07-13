package com.constantine.dialer.ui.screen.dashboard.listing

import androidx.lifecycle.LiveData
import com.constantine.domain.parcelable.ContactLog

class Listing {
    internal sealed class State {
        data class DisplayListing(val list: List<ContactLog>) : State()
    }
    internal sealed class Event
    internal interface ViewModel {
        val state: LiveData<State>
        val event: LiveData<Event>
    }
}
