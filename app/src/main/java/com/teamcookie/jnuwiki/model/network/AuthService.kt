package com.teamcookie.jnuwiki.model.network

import com.teamcookie.jnuwiki.model.dto.ResponseUserInfoDTO
import retrofit2.Call
import retrofit2.http.POST

interface AuthService {
    @POST("/members/info")
    fun getUserInfo() : Call<ResponseUserInfoDTO>
}