package com.example.task.ui.result.compose

import com.example.network.ResultCheck
import com.example.network.model.MatchModel
import com.example.network.repository.IApiRepository
import com.example.task.db.repository.IDBRepository
import com.example.task.ui.customComponent.compose.match.MatchUiModel
import javax.inject.Inject

class ResultUseCase @Inject constructor(
    private val apiRepository: IApiRepository,
    private val dbRepository: IDBRepository
) {

    suspend fun getResultAndMapUiModel() : ResultCheck<List<MatchUiModel>?> {
        val response = apiRepository.getMatchResults()
        val dbValue = dbRepository.getAllPrediction()

        if (response is ResultCheck.Success)
        {
            val model = response.data?.matches?.map { entity ->
                val prediction = dbValue.firstOrNull { it.team1 == entity.team1 && it.team2 == entity.team2 }
                MatchUiModel(
                    match = MatchModel(entity.team1,entity.team2),
                    prediction1 = prediction?.prediction1,
                    prediction2 = prediction?.prediction2,
                    team1_points = entity.team1_points,
                    team2_points = entity.team2_points
                )
            }
            return ResultCheck.Success(model,0)
        }

        return ResultCheck.Error((response as ResultCheck.Error).error)
    }

}