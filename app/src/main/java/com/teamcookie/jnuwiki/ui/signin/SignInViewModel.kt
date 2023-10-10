package com.teamcookie.jnuwiki.ui.signin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel(){

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun resetMessage(){
        _uiState.update {
            it.copy(message = "")
        }
    }

    fun updateField(
        email: String = uiState.value.email,
        nickname : String = uiState.value.nickname,
        pw: String = uiState.value.pw,
        repeat: String = uiState.value.repeat
    ){
        _uiState.update {
            it.copy(email = email, nickname =  nickname, pw = pw, repeat = repeat)
        }
    }

    fun submit(){
        
    }
}