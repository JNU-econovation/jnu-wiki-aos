package com.teamcookie.jnuwiki.model.dto

data class ResponseLoginDTO (
    val success : Boolean,
    val response : LoginInfo?,
    val error : ErrorDTO?,
)

data class LoginInfo(
    val id : Int,
    val role : String
)

