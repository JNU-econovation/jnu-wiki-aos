package com.teamcookie.jnuwiki.model.network


import com.teamcookie.jnuwiki.model.dto.RequestLoginDTO
import com.teamcookie.jnuwiki.model.dto.ResponseLoginDTO
import com.teamcookie.jnuwiki.model.dto.ResponseTokenDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MainService {
    @POST("/members/login")
    fun checkLogin(@Body request: RequestLoginDTO): Call<ResponseLoginDTO>

    @POST("/members/refresh-token")
    suspend fun issueToken() : Response<ResponseTokenDTO>
}