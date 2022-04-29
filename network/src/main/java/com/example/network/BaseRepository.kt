package com.example.network

import retrofit2.Response

/**
 * Created by Tarik MANKAOGLU on 29.04.2022.
 */
open class BaseRepository {

    fun <T> checkResponse(response: Response<T>): ResultCheck<T?> {
        return if (response.isSuccessful && response.body() != null) {
            ResultCheck.Success(response.body(), response.code())
        } else {
            apiCodeLayer(response)
        }
    }

    private fun <T> apiCodeLayer(
        response: Response<T>,
        isFinishActivity: Boolean = false
    ): ResultCheck<T?> {

        val message = if (response.isSuccessful && response.body() == null)
            "An unexpected error has occurred"
        else response.errorBody()?.string() ?: "An unexpected error has occurred"
        return ResultCheck.Error(
            error = NetworkError(
                message = message,
                code = response.code(),
                isFinishActivity = isFinishActivity
            )
        )
    }

    fun <T> exceptionError(): ResultCheck<T?> {
        return ResultCheck.Error(
            error = NetworkError(
                message = "An unexpected error has occurred",
                code = 404
            )
        )
    }

}