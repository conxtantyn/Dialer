package com.constantine.domain.server.repository

import com.constantine.domain.parcelable.Contact

interface CallRepository {
    fun getStatus(): Contact?
    fun onStateChanged(state: Int, number: String)
}
