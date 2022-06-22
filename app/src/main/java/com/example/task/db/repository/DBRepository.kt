package com.example.task.db.repository

import com.example.task.db.dao.RoomDao
import com.example.task.db.entity.PredictionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Tarik MANKAOGLU on 2.05.2022.
 */
class DBRepository @Inject constructor(private val dao: RoomDao) : IDBRepository {

    override fun addOrUpdatePrediction(entity: PredictionEntity) {
        dao.getPrediction(team1 = entity.team1, team2 = entity.team2)?.let { model ->
            entity.addedCurrentMill = model.addedCurrentMill
            entity.primaryKey = model.primaryKey
            dao.updatePrediction(entity)
            return
        }
        dao.addPrediction(entity)
    }

    override fun getPrediction(team1: String, team2: String): PredictionEntity? {
        return dao.getPrediction(team1 = team1, team2 = team2)
    }

    override fun getPrediction(): PredictionEntity? {
        return dao.getPrediction()
    }

    override fun getPredictionFlow(): Flow<List<PredictionEntity>> {
        return dao.getPredictionFlow()
    }

    override fun getAllPrediction(): List<PredictionEntity> {
        return dao.getAllPrediction()
    }

    override fun clearPrediction() {
        dao.clearPredictionTable()
    }
}