package com.teamcookie.jnuwiki.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.teamcookie.jnuwiki.model.dto.RequestLoginDTO
import com.teamcookie.jnuwiki.model.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private val repository = MainRepository
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateId(newId: String) {
        _uiState.update {
            it.copy(id = newId)
        }
    }

    fun updatePw(newPw: String) {
        _uiState.update {
            it.copy(pw = newPw)
        }
    }

    fun resetMessage() {
        _uiState.update {
            it.copy(message = "")
        }
    }

    fun submit() {
        repository.checkLogin(RequestLoginDTO(uiState.value.id, uiState.value.pw)) {result ->
            result.onFailure { throwable ->
                _uiState.update {
                    it.copy(message = throwable.message ?: "Error")
                }
            }.onSuccess { _ ->
                _uiState.update {
                    it.copy(isLogin = true)
                }
            }
        }
    }
}