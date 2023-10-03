package com.teamcookie.jnuwiki.model.dto

data class ResponseLoginDTO (
    val success : Boolean,
    val response : LoginInfo?,
    val error : ErrorInfo?,
)

data class LoginInfo(
    val id : Int,
    val role : String
)

data class ErrorInfo(
    val message: String,
    val status: Int
)