package com.example.task.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.task.db.entity.PredictionEntity
import kotlinx.coroutines.flow.Flow


/**
 * Created by Tarik MANKAOGLU on 10.08.2020.
 */
@Dao
interface RoomDao {

    @Insert
    fun addPrediction(vararg entity: PredictionEntity)

    @Update
    fun updatePrediction(order: PredictionEntity?)

    @Query("select * from PredictionTable where :team1 == team1 and :team2 = team2")
    fun getPrediction(team1: String, team2: String): PredictionEntity?

    @Query("select * from PredictionTable LIMIT 1")
    fun getPrediction(): PredictionEntity?

    @Query("select * from PredictionTable")
    fun getPredictionFlow(): Flow<List<PredictionEntity>?>

    @Query("select * from PredictionTable where :team1 == team1 and :team2 = team2")
    fun getPredictionWithPaging(team1: String, team2: String): PagingSource<Int, PredictionEntity>

    @Query("DELETE FROM PredictionTable")
    fun clearPredictionTable(): Int
}