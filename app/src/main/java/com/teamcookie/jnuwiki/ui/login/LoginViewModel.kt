package com.teamcookie.jnuwiki.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateId(newId : String){
        _uiState.update {
            it.copy(id = newId)
        }
    }

    fun updatePw(newPw : String){
        _uiState.update {
            it.copy(pw = newPw)
        }
    }

    fun submit() {

    }
}