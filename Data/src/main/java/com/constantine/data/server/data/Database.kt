package com.constantine.data.server.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.constantine.data.common.Constant.DATABASE_VERSION
import com.constantine.data.server.data.dao.CallLogDao
import com.constantine.data.server.entity.ContactLogEntity

@Database(
    entities = [
        ContactLogEntity::class
    ],
    version = DATABASE_VERSION
)
abstract class Database : RoomDatabase() {
    abstract fun callLogDao(): CallLogDao
}
