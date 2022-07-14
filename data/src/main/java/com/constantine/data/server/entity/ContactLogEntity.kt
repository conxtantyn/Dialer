package com.constantine.data.server.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["uId"], unique = true)])
data class ContactLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val uId: String,
    val name: String?,
    val number: String,
    val duration: Long,
    val date: Long
)
