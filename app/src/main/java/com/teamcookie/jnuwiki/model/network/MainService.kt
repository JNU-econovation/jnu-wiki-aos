package com.teamcookie.jnuwiki.model.network


import com.teamcookie.jnuwiki.model.dto.RequestLoginDTO
import com.teamcookie.jnuwiki.model.dto.ResponseLoginDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MainService {
    @POST("/members/login")
    fun checkLogin(@Body request: RequestLoginDTO): Call<ResponseLoginDTO>


}