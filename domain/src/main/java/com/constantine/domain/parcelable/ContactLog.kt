package com.constantine.domain.parcelable

data class ContactLog(
    val uId: String,
    val name: String?,
    val number: String,
    val duration: Long,
    val queryCount: Long,
    val date: Long
)
