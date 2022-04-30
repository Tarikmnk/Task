package com.example.network.api

import com.example.network.model.MatchResponse
import com.example.network.model.MatchResultsResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Tarik MANKAOGLU on 29.04.2022.
 */
interface ApiService {

    @GET("v3/13b2d902-d190-452f-ab01-2baf76865808")
    suspend fun getMatches(): Response<MatchResponse>

    @GET("v3/b861210a-2ec1-461b-bd0b-72bedc406c89")
    suspend fun getMatchResults(): Response<MatchResultsResponse>
}