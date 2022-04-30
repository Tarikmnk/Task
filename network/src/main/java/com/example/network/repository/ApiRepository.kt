package com.example.network.repository

import com.example.network.BaseRepository
import com.example.network.ResultCheck
import com.example.network.api.ApiService
import com.example.network.model.MatchResponse
import com.example.network.model.MatchResultsResponse
import javax.inject.Inject

interface IApiRepository {
    suspend fun getMatches(): ResultCheck<MatchResponse?>
    suspend fun getMatchResults(): ResultCheck<MatchResultsResponse?>
}

/**
 * Created by Tarik MANKAOGLU on 29.04.2022.
 */
class ApiRepository @Inject constructor(private val service: ApiService) : BaseRepository(),
    IApiRepository {

    override suspend fun getMatches(): ResultCheck<MatchResponse?> {
        return try {
            val response = service.getMatches()
            checkResponse(response)
        } catch (e: Exception) {
            exceptionError()
        }
    }

    override suspend fun getMatchResults(): ResultCheck<MatchResultsResponse?> {
        return try {
            val response = service.getMatchResults()
            checkResponse(response)
        } catch (e: Exception) {
            exceptionError()
        }
    }

}