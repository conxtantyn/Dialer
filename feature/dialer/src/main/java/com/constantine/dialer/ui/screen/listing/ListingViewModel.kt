package com.constantine.dialer.ui.screen.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.constantine.core.component.SingleLiveEvent
import javax.inject.Inject

internal class ListingViewModel @Inject constructor() : ViewModel(), Listing.ViewModel {
    private val mutableState = MutableLiveData<Listing.State>()

    private val mutableEvent = SingleLiveEvent<Listing.Event>()

    override val state: LiveData<Listing.State> get() = mutableState

    override val event: LiveData<Listing.Event> get() = mutableEvent
}
