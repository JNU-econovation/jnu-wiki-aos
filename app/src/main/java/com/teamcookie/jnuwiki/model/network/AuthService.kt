package com.teamcookie.jnuwiki.model.network

import com.teamcookie.jnuwiki.model.dto.ResponseUserInfoDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @GET("/members/info")
    suspend fun getUserInfo() : Response<ResponseUserInfoDTO>
}