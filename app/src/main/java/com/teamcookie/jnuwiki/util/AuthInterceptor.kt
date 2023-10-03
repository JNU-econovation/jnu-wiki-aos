package com.teamcookie.jnuwiki.util

import com.teamcookie.jnuwiki.MainApplication
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request().newBuilder().addHeader(
            "Authorization",
            MainApplication.prefs.token ?: ""
        ).build()
        return chain.proceed(req)
    }
}