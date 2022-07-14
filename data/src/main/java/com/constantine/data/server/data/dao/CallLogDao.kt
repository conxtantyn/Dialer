package com.constantine.data.server.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.constantine.data.server.entity.ContactLogEntity
import com.constantine.domain.parcelable.ContactLog

@Dao
interface CallLogDao {
    @Query("SELECT *, (SELECT COUNT(*) FROM ContactLogEntity WHERE number = entity.number) as queryCount FROM ContactLogEntity AS entity GROUP BY number ORDER BY date DESC")
    suspend fun getList(): List<ContactLog>

    @Query("SELECT *, (SELECT COUNT(*) FROM ContactLogEntity WHERE number = entity.number) as queryCount FROM ContactLogEntity AS entity GROUP BY number ORDER BY date DESC")
    fun getAll(): LiveData<List<ContactLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(logs: List<ContactLogEntity>)
}
