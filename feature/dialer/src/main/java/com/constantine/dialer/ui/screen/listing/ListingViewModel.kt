package com.constantine.dialer.ui.screen.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.constantine.core.component.SingleLiveEvent
import com.constantine.domain.parcelable.ContactLog
import com.constantine.domain.server.usecase.CallLogsUsecase
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class ListingViewModel @Inject constructor(
    private val callLogsUsecase: CallLogsUsecase
) : ViewModel(), Listing.ViewModel {
    private val mutableState = MediatorLiveData<Listing.State>()

    private val mutableEvent = SingleLiveEvent<Listing.Event>()

    private var logSource: LiveData<List<ContactLog>>? = null

    override val state: LiveData<Listing.State> get() = mutableState

    override val event: LiveData<Listing.Event> get() = mutableEvent

    fun refreshLog() {
        viewModelScope.launch {
            logSource?.let { mutableState.removeSource(it) }
            logSource = callLogsUsecase.log().also {
                mutableState.addSource(it) { logs ->
                    mutableState.value = Listing.State.DisplayListing(logs)
                }
            }
        }
    }
}
