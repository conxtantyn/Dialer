package com.constantine.data.server

import android.content.Context
import androidx.room.Room
import com.constantine.data.common.Constant
import com.constantine.data.server.data.Database
import com.constantine.data.server.data.dao.CallLogDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database {
        return Room.databaseBuilder(context, Database::class.java, Constant.DATABASE).build()
    }

    @Provides
    @Singleton
    fun providesCallLogDao(database: Database): CallLogDao = database.callLogDao()
}
