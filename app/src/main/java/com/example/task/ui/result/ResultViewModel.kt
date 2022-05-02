package com.example.task.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.network.ResultCheck
import com.example.network.model.MatchResultModel
import com.example.network.model.MatchResultsResponse
import com.example.network.repository.IApiRepository
import com.example.task.base.BaseViewModel
import com.example.task.db.entity.PredictionEntity
import com.example.task.db.repository.DBRepository
import com.example.task.db.repository.IDBRepository
import com.example.task.ui.adapter.ResultAdapter
import com.example.task.ui.main.model.MainUiModel
import com.example.task.ui.result.model.ResultUiModel
import com.example.task.utils.sharedHelper.LocalStorage
import com.example.task.utils.sharedHelper.SharedHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Tarik MANKAOGLU on 2.05.2022.
 */
@HiltViewModel
class ResultViewModel @Inject constructor(
    private val apiRepository: IApiRepository,
    val dbRepository: IDBRepository,
    var localStorage: LocalStorage,
) : BaseViewModel() {

    private val _resultLiveData = MutableLiveData<List<MatchResultModel>>()
    val resultLiveData: LiveData<List<MatchResultModel>> get() = _resultLiveData

    val adapter = ResultAdapter()

    init {
        getMatchResults()
        localStorage.putString(SharedHelper.keyLastScreenTag, ResultActivity.TAG)
    }

    private fun getMatchResults() {
        viewModelScope.launch {
            when (val response = apiRepository.getMatchResults()) {
                is ResultCheck.Success -> {
                    _resultLiveData.value = response.data!!.matches
                }
                is ResultCheck.Error -> {
                    errorLiveData.value = response.error
                }
            }
        }
    }

    fun getPredictionFlow(): Flow<List<PredictionEntity>?> {
        return dbRepository.getPredictionFlow()
    }

}