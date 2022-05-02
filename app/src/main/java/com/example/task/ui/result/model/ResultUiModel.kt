package com.example.task.ui.result.model

import com.example.network.BaseModel
import com.example.network.model.MatchResultModel

/**
 * Created by Tarik MANKAOGLU on 2.05.2022.
 */
data class ResultUiModel(

    val match: MatchResultModel,
    var prediction1: Int? = null,
    var prediction2: Int? = null

) : BaseModel()
