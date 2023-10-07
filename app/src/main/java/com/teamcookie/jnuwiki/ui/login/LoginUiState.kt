package com.teamcookie.jnuwiki.ui.login

data class LoginUiState(
    val id : String = "",
    val pw : String = "",
    val message : String = "",
    val isLogin : Boolean = false
)