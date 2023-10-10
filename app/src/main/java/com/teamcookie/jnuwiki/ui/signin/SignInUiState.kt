package com.teamcookie.jnuwiki.ui.signin

data class SignInUiState(
    val isSignIn: Boolean = false,
    val message: String = "",
    val email: String = "",
    val nickname : String = "",
    val pw : String = "",
    val repeat : String = ""
)
