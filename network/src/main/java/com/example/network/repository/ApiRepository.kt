package com.example.network.repository

import com.example.network.BaseRepository
import com.example.network.api.ApiService
import javax.inject.Inject

interface IApiRepository {

}

/**
 * Created by Tarik MANKAOGLU on 29.04.2022.
 */
class ApiRepository @Inject constructor(private val service: ApiService) : BaseRepository(),
    IApiRepository {



}