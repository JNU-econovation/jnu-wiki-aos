package com.teamcookie.jnuwiki.util

import com.teamcookie.jnuwiki.MainApplication
import okhttp3.Interceptor
import okhttp3.Response

class RefreshInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request().newBuilder().addHeader(
            "SET-COOKIE",
            MainApplication.prefs.refresh ?: ""
        ).build()
        return chain.proceed(req)
    }
}