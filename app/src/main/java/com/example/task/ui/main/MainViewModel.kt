package com.example.task.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.network.ResultCheck
import com.example.network.model.MatchResponse
import com.example.network.repository.ApiRepository
import com.example.network.repository.IApiRepository
import com.example.task.base.BaseViewModel
import com.example.task.ui.adapter.MatchesAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Tarik MANKAOGLU on 30.04.2022.
 */
@HiltViewModel
class MainViewModel @Inject constructor(val apiRepository: IApiRepository) : BaseViewModel() {

    private val _matchLiveData = MutableLiveData<MatchResponse>()
    val matchLiveData: LiveData<MatchResponse> get() = _matchLiveData

    val adapter = MatchesAdapter()

    init {
        getMatches()
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
}