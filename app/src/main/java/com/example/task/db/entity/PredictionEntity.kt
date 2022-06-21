package com.example.task.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.network.BaseModel

/**
 * Created by Tarik MANKAOGLU on 10.08.2020.
 */
@Entity(tableName = "PredictionTable")
data class PredictionEntity(
    @PrimaryKey(autoGenerate = true)
    var primaryKey: Int = 0,
    var team1: String = "",
    var team2: String = "",
    var prediction1: Int? = null,
    var prediction2: Int? = null,
    var addedCurrentMill: Long = 0
) : BaseModel()