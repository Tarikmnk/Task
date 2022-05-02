package com.example.task.utils.sharedHelper

/**
 * Created by Tarik MANKAOGLU on 16.05.2020.
 */
interface Mapper {

    fun <T> convertToObjectFromJson(json: String?, classOfT: Class<T>): T?

    fun convertToJsonFromObject(value: Any?): String?

}