package com.teamcookie.jnuwiki.model.repository

import com.google.gson.Gson
import com.teamcookie.jnuwiki.MainApplication
import com.teamcookie.jnuwiki.model.dto.RequestLoginDTO
import com.teamcookie.jnuwiki.model.dto.ResponseLoginDTO
import com.teamcookie.jnuwiki.model.dto.BaseResponseDTO
import com.teamcookie.jnuwiki.model.dto.ResponseUserInfoDTO
import com.teamcookie.jnuwiki.model.dto.ResultInfo
import com.teamcookie.jnuwiki.model.network.MainClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MainRepository {
    fun checkLogin(request: RequestLoginDTO, callback: (Result<Unit>) -> (Unit)){

        MainClient.mainService.checkLogin(request).enqueue(object: Callback<ResponseLoginDTO>{
            override fun onResponse(
                call: Call<ResponseLoginDTO>,
                response: Response<ResponseLoginDTO>
            ) {
                if(response.isSuccessful.not()){
                    val temp = Gson().fromJson(response.errorBody()?.string(),ResponseLoginDTO::class.java)
                    callback(Result.failure(Exception(temp.error!!.message)))
                }else{
                    MainApplication.prefs.token = response.headers()["AUTHORIZATION"]
                    MainApplication.prefs.refresh = response.headers()["SET-COOKIE"]
                    callback(Result.success(Unit))
                }
            }

            override fun onFailure(call: Call<ResponseLoginDTO>, t: Throwable) {
                callback(Result.failure(Exception(t.message)))
            }
        })
    }

    suspend fun getInfo() : Result<ResultInfo>{
        return try {
            val response = MainClient.authService.getUserInfo()
            if(response.isSuccessful.not()){
                val temp = Gson().fromJson(response.errorBody()?.string(),ResponseUserInfoDTO::class.java)
                Result.success(ResultInfo(temp.error!!.message,temp.error.status))
            }else{
                Result.success(ResultInfo(response.body()!!.response!!.nickName,200))
            }
        }catch (e: Exception){
            Result.failure(Exception(e.message))
        }
    }

    suspend fun issueAccessToken(): Result<Int> {
        return try {
            val response = MainClient.mainService.issueToken()
            if(response.isSuccessful.not()){
                val temp = Gson().fromJson(response.errorBody()?.string(),BaseResponseDTO::class.java)
                Result.success(temp.error!!.status)
            }else{
                Result.success(200)
            }
        }catch (e: Exception){
            Result.failure(Exception(e.message))
        }
    }
}