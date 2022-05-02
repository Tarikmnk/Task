package com.example.task.di

import android.content.Context
import android.content.SharedPreferences
import com.example.task.utils.sharedHelper.LocalStorage
import com.example.task.utils.sharedHelper.Mapper
import com.example.task.utils.sharedHelper.MapperImpl
import com.example.task.utils.sharedHelper.SharedHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Tarik MANKAOGLU on 24.12.2021.
 */
@Module
@InstallIn(SingletonComponent::class)
class SharedPrefModule {

    @Singleton
    @Provides
    fun sharedMapper(): Mapper = MapperImpl()

    @Singleton
    @Provides
    fun localStorage(@ApplicationContext appContext: Context, sharedMapper: Mapper): LocalStorage =
        SharedHelper(appContext, sharedMapper)

    @Singleton
    @Provides
    fun sharedPreferences(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences(
            SharedHelper.sharedFirstKey, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun sharedPreferencesEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor =
        sharedPreferences.edit()

    @Singleton
    @Provides
    fun createSharedHelper(@ApplicationContext context: Context, mapper: Mapper): SharedHelper =
        SharedHelper(context,mapper)

}