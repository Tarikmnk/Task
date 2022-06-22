package com.example.task.ui.customComponent.compose.match

import com.example.network.model.MatchModel

data class MatchUiModel(
    val match: MatchModel,
    var prediction1: Int? = null,
    var prediction2: Int? = null,
    val team1_points: Int? = null,
    val team2_points: Int? = null,
)