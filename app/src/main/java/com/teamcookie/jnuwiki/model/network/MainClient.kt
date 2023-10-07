package com.teamcookie.jnuwiki.model.network

import com.google.gson.GsonBuilder
import com.teamcookie.jnuwiki.Const
import com.teamcookie.jnuwiki.util.AuthInterceptor
import com.teamcookie.jnuwiki.util.RefreshInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object MainClient {
    //TODO Refactor
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10,TimeUnit.SECONDS)
        .writeTimeout(10,TimeUnit.SECONDS)
        .readTimeout(10,TimeUnit.SECONDS)
        .build()

    private val okHttpClientWithAuthInterceptor = OkHttpClient.Builder()
        .connectTimeout(10,TimeUnit.SECONDS)
        .writeTimeout(10,TimeUnit.SECONDS)
        .readTimeout(10,TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor())
        .build()

    private val okHttpClientWithRefreshInterceptor = OkHttpClient.Builder()
        .connectTimeout(10,TimeUnit.SECONDS)
        .writeTimeout(10,TimeUnit.SECONDS)
        .readTimeout(10,TimeUnit.SECONDS)
        .addInterceptor(RefreshInterceptor())
        .build()

    private var gson = GsonBuilder().setLenient().create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Const.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    private val retrofitWithAuthInterceptor = Retrofit.Builder()
        .baseUrl(Const.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClientWithAuthInterceptor)
        .build()

    private val retrofitWithRefreshInterceptor = Retrofit.Builder()
        .baseUrl(Const.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClientWithRefreshInterceptor)
        .build()


    val authService : AuthService by lazy {
        retrofitWithAuthInterceptor.create(AuthService::class.java)
    }

    val refreshService : AuthService by lazy {
        retrofitWithRefreshInterceptor.create(AuthService::class.java)
    }

    val mainService : MainService by lazy {
        retrofit.create(MainService::class.java)
    }
}