package com.example.network.model

import com.example.network.BaseModel
import com.example.network.BaseResponse

/**
 * Created by Tarik MANKAOGLU on 29.04.2022.
 */
data class MatchResponse(
    val matches: List<MatchModel>
) : BaseResponse()

data class MatchModel(
    val team1: String,
    val team2: String,
) : BaseModel()

data class MatchResultsResponse(
    val matches: List<MatchResultModel>
) : BaseResponse()

data class MatchResultModel(
    val team1: String,
    val team2: String,
    val team1_points: Int,
    val team2_points: Int,
) : BaseModel()
