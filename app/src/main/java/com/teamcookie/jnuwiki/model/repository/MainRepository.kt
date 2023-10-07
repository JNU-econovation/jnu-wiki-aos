package com.teamcookie.jnuwiki.model.repository

import android.util.Log
import com.google.gson.Gson
import com.teamcookie.jnuwiki.MainApplication
import com.teamcookie.jnuwiki.model.dto.RequestLoginDTO
import com.teamcookie.jnuwiki.model.dto.ResponseLoginDTO
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
}