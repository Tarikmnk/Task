package com.example.task.di

import android.content.Context
import androidx.room.Room
import com.example.task.db.DataBase
import com.example.task.db.dao.RoomDao
import com.example.task.db.repository.DBRepository
import com.example.task.db.repository.IDBRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Tarik MANKAOGLU on 7.03.2022.
 */

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun getRoom(@ApplicationContext appContext: Context): DataBase {
        return Room.databaseBuilder(
            appContext,
            DataBase::class.java,
            "DataBase"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun roomDao(database: DataBase): RoomDao {
        return database.roomDao()
    }

    @Singleton
    @Provides
    fun createDBRepository(dao: RoomDao): IDBRepository {
        return DBRepository(dao)
    }
}