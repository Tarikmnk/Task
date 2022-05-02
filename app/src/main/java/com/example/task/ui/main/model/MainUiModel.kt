package com.example.task.ui.main.model

import com.example.network.BaseModel
import com.example.network.model.MatchModel

/**
 * Created by Tarik MANKAOGLU on 30.04.2022.
 */
data class MainUiModel(

    val match: MatchModel,
    var prediction1: Int? = null,
    var prediction2: Int? = null

) : BaseModel()
