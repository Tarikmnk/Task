package com.example.task.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.network.NetworkError

/**
 * Created by Tarik MANKAOGLU on 29.04.2022.
 */

open class BaseViewModel : ViewModel() {

    val eventShowOrHideProgress = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<NetworkError>()
}