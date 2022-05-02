package com.example.task.utils.sharedHelper

import com.google.gson.GsonBuilder

/**
 * Created by Tarik MANKAOGLU on 16.05.2020.
 */
class MapperImpl : Mapper {

    private val gson = GsonBuilder().create()

    override fun <T> convertToObjectFromJson(json: String?, classOfT: Class<T>): T? =
        gson.fromJson(json, classOfT)

    override fun convertToJsonFromObject(value: Any?): String? = gson.toJson(value)
}