package com.example.task.ui.main.compose

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.*
import com.example.network.NetworkError
import com.example.network.ResultCheck
import com.example.network.model.MatchModel
import com.example.network.model.MatchResponse
import com.example.network.repository.IApiRepository
import com.example.task.base.BaseViewModel
import com.example.task.db.entity.PredictionEntity
import com.example.task.db.repository.IDBRepository
import com.example.task.ui.adapter.MatchesAdapter
import com.example.task.ui.customComponent.compose.match.MatchUiModel
import com.example.task.ui.main.model.MainUiModel
import com.example.task.utils.createBetDialog
import com.example.task.utils.sharedHelper.LocalStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Tarik MANKAOGLU on 30.04.2022.
 */
@HiltViewModel
class MainScreenViewModel @Inject constructor(
    var apiRepository: IApiRepository,
    val dbRepository: IDBRepository,
    val useCase: MainUseCase,
    var localStorage: LocalStorage,
) : BaseViewModel() {

    private val _matchLiveData = MutableStateFlow(MatchResponse(listOf()))
    val matchLiveData: StateFlow<MatchResponse> get() = _matchLiveData

    val getStateUi = getPredictionFlow()
        .catch { catch ->
            catch.localizedMessage
            errorLiveData.value = NetworkError(catch.localizedMessage ?: "", 404, false)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), listOf())

    init {
        getData()
    }

    private fun getMatches() {
        viewModelScope.launch {
            when (val response = useCase.getMatchesAndInsertToDb()) {
                is ResultCheck.Success -> {
                    _matchLiveData.emit(response.data!!)
                }
                is ResultCheck.Error -> {
                    errorLiveData.value = response.error
                }
            }
        }
    }

    private fun getData() {
        dbRepository.getPrediction()?.let {
            if (it.addedCurrentMill + (1000 * 60) > System.currentTimeMillis()) {
                return
            }
        }
        getMatches()
    }

    private fun getPredictionFlow(): Flow<List<PredictionEntity>> {
        return dbRepository.getPredictionFlow()
    }

    fun addPrediction(context: Context, item: MatchUiModel) {
        context.createBetDialog(
            team1 = item.match.team1,
            team2 = item.match.team2,
            prediction1 = item.prediction1 ?: 0,
            prediction2 = item.prediction2 ?: 0,
            limitLower = 0
        ) { prediction1, prediction2 ->
            item.prediction1 = prediction1
            item.prediction2 = prediction2

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
}