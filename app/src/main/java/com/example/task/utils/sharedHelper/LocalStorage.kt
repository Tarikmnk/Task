package com.example.task.utils.sharedHelper

/**
 * Created by Tarik MANKAOGLU on 16.05.2020.
 */
interface LocalStorage {

    fun putInt(key: String, value: Int)

    fun putFloat(key: String, value: Float)

    fun putLong(key: String, value: Long)

    fun putBoolean(key: String, value: Boolean)

    fun putString(key: String, value: String?)

    fun putObject(key: String, value: Any?)

    fun getInt(key: String, defaultValue: Int): Int

    fun getFloat(key: String, defaultValue: Float): Float

    fun getLong(key: String, defaultValue: Long): Long

    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun getString(key: String, defaultValue: String = ""): String

    fun <T> getObject(key: String, classOfT: Class<T>): T?

    fun remove(key: String)

    fun clear()

    fun getAllKeys(): List<String>
}