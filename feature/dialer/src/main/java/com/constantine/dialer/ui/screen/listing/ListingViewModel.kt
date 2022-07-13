package com.constantine.dialer.ui.screen.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.constantine.core.component.SingleLiveEvent
import com.constantine.domain.server.usecase.CallLogsUsecase
import com.constantine.domain.usecase.InstallTimestampUsecase
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class ListingViewModel @Inject constructor(
    private val callLogsUsecase: CallLogsUsecase,
    private val installTimestampUsecase: InstallTimestampUsecase
) : ViewModel(), Listing.ViewModel {
    private val mutableState = MediatorLiveData<Listing.State>()

    private val mutableEvent = SingleLiveEvent<Listing.Event>()

    override val state: LiveData<Listing.State> get() = mutableState

    override val event: LiveData<Listing.Event> get() = mutableEvent

    fun initialize() {
        viewModelScope.launch {
            mutableState.addSource(callLogsUsecase.log(installTimestampUsecase.timestamp())) {
                mutableState.value = Listing.State.DisplayListing(it)
            }
        }
    }
}
