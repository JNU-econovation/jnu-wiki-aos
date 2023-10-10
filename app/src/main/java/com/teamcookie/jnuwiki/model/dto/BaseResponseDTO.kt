package com.teamcookie.jnuwiki.model.dto

data class BaseResponseDTO(
    val success : Boolean,
    val response : String?,
    val error : StatusDTO?,
)
