package com.example.task.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.network.ResultCheck
import com.example.network.model.MatchResponse
import com.example.network.repository.IApiRepository
import com.example.task.base.BaseViewModel
import com.example.task.db.entity.PredictionEntity
import com.example.task.db.repository.IDBRepository
import com.example.task.ui.adapter.MatchesAdapter
import com.example.task.ui.main.model.MainUiModel
import com.example.task.utils.sharedHelper.LocalStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Tarik MANKAOGLU on 30.04.2022.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepository: IApiRepository,
    val dbRepository: IDBRepository,
    var localStorage: LocalStorage,
) : BaseViewModel() {

    private val _matchLiveData = MutableLiveData<MatchResponse>()
    val matchLiveData: LiveData<MatchResponse> get() = _matchLiveData

    val adapter = MatchesAdapter {
        addPrediction(it)
    }

    init {
        getData()
    }

    fun getMatches() {
        viewModelScope.launch {
            when (val response = apiRepository.getMatches()) {
                is ResultCheck.Success -> {
                    _matchLiveData.value = response.data!!
                }
                is ResultCheck.Error -> {
                    errorLiveData.value = response.error
                }
            }
        }
    }

    fun getData() {
        dbRepository.getPrediction()?.let {
            if (it.addedCurrentMill + (1000 * 5) > System.currentTimeMillis()) {
                return
            }
        }
        getMatches()
    }

    fun getPredictionFlow(): Flow<List<PredictionEntity>?> {
        return dbRepository.getPredictionFlow()
    }

    fun getPrediction(team1: String, team2: String): PredictionEntity? {
        return dbRepository.getPrediction(team1, team2)
    }

    private fun addPrediction(item: MainUiModel) {
        val entity = PredictionEntity(
            team1 = item.match.team1,
            team2 = item.match.team2,
            prediction1 = item.prediction1,
            prediction2 = item.prediction2,
            addedCurrentMill = System.currentTimeMillis()
        )
        dbRepository.addOrUpdatePrediction(entity)
    }
}