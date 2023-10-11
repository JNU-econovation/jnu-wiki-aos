package com.teamcookie.jnuwiki.model.network


import com.teamcookie.jnuwiki.model.dto.RequestLoginDTO
import com.teamcookie.jnuwiki.model.dto.ResponseLoginDTO
import com.teamcookie.jnuwiki.model.dto.BaseResponseDTO
import com.teamcookie.jnuwiki.model.dto.RequestCheckEmailDTO
import com.teamcookie.jnuwiki.model.dto.RequestCheckNicknameDTO
import com.teamcookie.jnuwiki.model.dto.RequestSignInDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MainService {
    @POST("/members/login")
    fun checkLogin(@Body request: RequestLoginDTO): Call<ResponseLoginDTO>

    @POST("/members/refresh-token")
    suspend fun issueToken() : Response<BaseResponseDTO>

    @POST("/members/check/email")
    suspend fun checkEmail(@Body request: RequestCheckEmailDTO): Response<BaseResponseDTO>

    @POST("/members/check/nickname")
    suspend fun checkNickname(@Body request: RequestCheckNicknameDTO): Response<BaseResponseDTO>

    @POST("/members/join")
    suspend fun signIn(@Body request: RequestSignInDTO): Response<BaseResponseDTO>
}