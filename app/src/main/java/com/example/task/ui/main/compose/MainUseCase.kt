package com.example.task.ui.main.compose

import com.example.network.ResultCheck
import com.example.network.model.MatchResponse
import com.example.network.repository.IApiRepository
import com.example.task.db.entity.PredictionEntity
import com.example.task.db.repository.IDBRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class MainUseCase @Inject constructor(
    var apiRepository: IApiRepository,
    val dbRepository: IDBRepository
) {

    suspend fun getMatchesAndInsertToDb():ResultCheck<MatchResponse?> {
        val response = apiRepository.getMatches()
        if (response is ResultCheck.Success) {
            val currentMill = System.currentTimeMillis()
            response.data?.matches?.map {
                val prediction = getPrediction(it.team1, it.team2)
                val entity = PredictionEntity(
                    team1 = it.team1,
                    team2 = it.team2,
                    prediction1 = prediction?.prediction1,
                    prediction2 = prediction?.prediction2,
                    addedCurrentMill = currentMill
                )
                dbRepository.addOrUpdatePrediction(
                    entity
                )
                entity
            }
        }
        return response
    }

    private fun getPrediction(team1: String, team2: String): PredictionEntity? {
        return dbRepository.getPrediction(team1, team2)
    }
}