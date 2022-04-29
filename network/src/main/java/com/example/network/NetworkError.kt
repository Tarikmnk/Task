package com.example.network

/**
 * Created by Tarik MANKAOGLU on 29.04.2022.
 */
data class NetworkError(var message: String, var code: Int, val isFinishActivity: Boolean = false)