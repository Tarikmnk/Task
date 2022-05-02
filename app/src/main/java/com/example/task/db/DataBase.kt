package com.example.task.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task.db.dao.RoomDao
import com.example.task.db.entity.PredictionEntity


/**
 * Created by Tarik MANKAOGLU on 10.08.2020.
 */
@Database(
    entities = [PredictionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DataBase : RoomDatabase() {
    abstract fun roomDao(): RoomDao
}
