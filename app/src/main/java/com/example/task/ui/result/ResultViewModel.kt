package com.example.task.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.network.ResultCheck
import com.example.network.model.MatchResultsResponse
import com.example.network.repository.IApiRepository
import com.example.task.base.BaseViewModel
import com.example.task.ui.adapter.ResultAdapter
import com.example.task.ui.main.model.MainUiModel
import com.example.task.ui.result.model.ResultUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Tarik MANKAOGLU on 2.05.2022.
 */
@HiltViewModel
class ResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val apiRepository: IApiRepository
) : BaseViewModel() {

    val matchModel =
        savedStateHandle.get<List<MainUiModel>>(ResultActivity.MODEL) as List<MainUiModel>

    private val _resultLiveData = MutableLiveData<List<ResultUiModel>>()
    val resultLiveData: LiveData<List<ResultUiModel>> get() = _resultLiveData

    val adapter = ResultAdapter()

    init {
        getMatchResults()
    }

    private fun getMatchResults() {
        viewModelScope.launch {
            when (val response = apiRepository.getMatchResults()) {
                is ResultCheck.Success -> {
                    _resultLiveData.value = mapResultUiModel(response.data!!)
                }
                is ResultCheck.Error -> {
                    errorLiveData.value = response.error
                }
            }
        }
    }

    private fun mapResultUiModel(data: MatchResultsResponse): List<ResultUiModel> {
        return data.matches.map { result ->
            matchModel.firstOrNull { it.match.team1 == result.team1 && it.match.team2 == result.team2 }
                ?.let {
                    ResultUiModel(result, it.prediction1, it.prediction2)
                }
        } as List<ResultUiModel>
    }

}