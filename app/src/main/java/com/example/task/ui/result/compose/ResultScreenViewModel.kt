package com.example.task.ui.result.compose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.network.ResultCheck
import com.example.task.base.BaseViewModel
import com.example.task.ui.customComponent.compose.match.MatchUiModel
import com.example.task.ui.result.ResultActivity
import com.example.task.utils.sharedHelper.LocalStorage
import com.example.task.utils.sharedHelper.SharedHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Tarik MANKAOGLU on 2.05.2022.
 */
@HiltViewModel
class ResultScreenViewModel @Inject constructor(
    val resultUseCase :ResultUseCase,
    val localStorage: LocalStorage,
) : BaseViewModel() {

    private val _resultLiveData = MutableLiveData<List<MatchUiModel>>()
    val resultLiveData: LiveData<List<MatchUiModel>> get() = _resultLiveData

    init {
        getMatchResults()
        localStorage.putString(SharedHelper.keyLastScreenTag, ResultActivity.TAG)
    }

    private fun getMatchResults() {
        viewModelScope.launch {
            when (val response = resultUseCase.getResultAndMapUiModel()) {
                is ResultCheck.Success -> {
                    _resultLiveData.value = response.data!!
                }
                is ResultCheck.Error -> {
                    errorLiveData.value = response.error
                }
            }
        }
    }

}