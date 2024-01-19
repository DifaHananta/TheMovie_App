package com.project.themovie.data.retrofit

import com.project.themovie.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiConfig {

    private const val BASE_URL = BuildConfig.BASE_URL
    private const val ACCESS_TOKEN = BuildConfig.ACCESS_TOKEN

    fun getApiService(): ApiService {
        val loggingInterceptor =
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
        val authInterceptor = Interceptor { chain ->
            val request = chain.request()
            val requestHeader = request.newBuilder()
                .addHeader("Authorization", "Bearer $ACCESS_TOKEN")
                .build()
            chain.proceed(requestHeader)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}