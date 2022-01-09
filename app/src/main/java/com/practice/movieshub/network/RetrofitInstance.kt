package com.practice.movieshub.network

import com.google.gson.GsonBuilder
import com.practice.movieshub.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object {
        private val client by lazy {
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .readTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .connectTimeout(70, TimeUnit.SECONDS)
                .build()
        }
        private val gson by lazy {
            GsonBuilder().setLenient().create()
        }
        private val retrofit by lazy {
            Retrofit.Builder().client(client).baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        val api: RetroService by lazy {
            retrofit.create(RetroService::class.java)
        }

    }
}