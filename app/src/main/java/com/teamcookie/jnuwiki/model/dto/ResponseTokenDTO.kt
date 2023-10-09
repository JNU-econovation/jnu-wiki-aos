package com.teamcookie.jnuwiki.model.dto

data class ResponseTokenDTO(
    val success : Boolean,
    val response : String?,
    val error : ErrorDTO?,
)
