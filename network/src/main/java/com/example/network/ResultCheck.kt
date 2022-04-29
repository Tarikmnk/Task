package com.example.network

/**
 * Created by Tarik MANKAOGLU on 29.04.2022.
 */
sealed class ResultCheck<T> {
    data class Success<T>(val data :T,val code:Int) : ResultCheck<T>()
    data class Error<T>(val error : NetworkError) : ResultCheck<T>()
}