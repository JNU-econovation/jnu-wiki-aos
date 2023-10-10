package com.teamcookie.jnuwiki.model.dto

data class ResponseUserInfoDTO(
    val success: Boolean,
    val response: UserInfo?,
    val error: ErrorDTO?
)

data class UserInfo(
    val id: Int,
    val nickName: String,
    val password: String
)

data class ResultInfo(
    val name: String,
    val result: Int
)