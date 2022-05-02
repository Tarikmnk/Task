package com.example.task.db.repository

import com.example.task.db.entity.PredictionEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Tarik MANKAOGLU on 2.05.2022.
 */
interface IDBRepository {
    fun addOrUpdatePrediction(entity: PredictionEntity)
    fun getPrediction(team1: String, team2: String): PredictionEntity?
    fun getPrediction(): PredictionEntity?
    fun getPredictionFlow(): Flow<List<PredictionEntity>?>
    fun clearPrediction()
}