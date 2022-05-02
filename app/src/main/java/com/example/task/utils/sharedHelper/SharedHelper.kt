package com.example.task.utils.sharedHelper

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Tarik MANKAOGLU on 16.05.2020.
 */
class SharedHelper @Inject constructor(@ApplicationContext context: Context, var mapper: Mapper) :
    LocalStorage {

    companion object {
        const val sharedFirstKey = "default"

        const val keyLastScreenTag = "last_screen_tag"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(sharedFirstKey, Context.MODE_PRIVATE)

    override fun putInt(key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    override fun putFloat(key: String, value: Float) {
        val editor = sharedPreferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    override fun putLong(key: String, value: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    override fun putBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    override fun putString(key: String, value: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun putObject(key: String, value: Any?) {
        val editor = sharedPreferences.edit()
        editor.putString(key, mapper.convertToJsonFromObject(value))
        editor.apply()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    override fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, "") ?: defaultValue
    }

    override fun <T> getObject(key: String, classOfT: Class<T>): T? {
        val json = sharedPreferences.getString(key, null)
        return mapper.convertToObjectFromJson(json, classOfT)
    }

    override fun remove(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    override fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    override fun getAllKeys(): List<String> {
        val keys = mutableListOf<String>()
        for ((key) in sharedPreferences.all) {
            keys.add(key)
        }
        return keys
    }

}