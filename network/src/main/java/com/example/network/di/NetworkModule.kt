package com.example.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.example.network.BuildConfig
import com.example.network.api.ApiService
import com.example.network.repository.ApiRepository
import com.example.network.repository.IApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Tarik MANKAOGLU on 29.04.2022.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object{
        const val baseUrl = "https://run.mocky.io/"
    }

    // we create instance OkHttpClient
    @Singleton
    @Provides
    fun createOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .callTimeout(60L, TimeUnit.SECONDS)
        .writeTimeout(60L, TimeUnit.SECONDS)
        .connectTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(createHttpLoggingInterceptor(BuildConfig.DEBUG))
        .build()

    // we create instance HttpLoggingInterceptor
    @Singleton
    @Provides
    fun createHttpLoggingInterceptor(debugMode: Boolean): Interceptor =
        HttpLoggingInterceptor().apply {
            level = if (debugMode) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    // we create instance gson for retrofit addConverterFactory
    @Singleton
    @Provides
    fun createGsonBuilder(): Gson =
        GsonBuilder()
            .setLenient()
            .create()

    // we create instance Retrofit
    @Singleton
    @Provides
    fun createRetrofit(okHttpClient: OkHttpClient,gson:Gson): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    // we create instance ApiService
    @Singleton
    @Provides
    fun apiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun createIAuthRepository(service: ApiService): IApiRepository = ApiRepository(service)
}