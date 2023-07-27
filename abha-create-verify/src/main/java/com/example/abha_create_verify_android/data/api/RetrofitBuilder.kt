package com.example.abha_create_verify_android.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

    object RetrofitBuilder {

        var BASE_URL = "https://dev.gdobahmni.click"
        private const val HEADER_AUTHORIZATION = "Authorization"
        var AUTH_TOKEN = ""

        private fun getRetrofit(): Retrofit {

            val okHttpClient = OkHttpClient.Builder().apply {
                addInterceptor(
                    Interceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header(HEADER_AUTHORIZATION, "Bearer $AUTH_TOKEN")
                        readTimeout(10, TimeUnit.SECONDS)
                        connectTimeout(5, TimeUnit.SECONDS)
                        return@Interceptor chain.proceed(builder.build())
                    }
                )

            }.build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val apiService: ApiService = getRetrofit().create(ApiService::class.java)
    }