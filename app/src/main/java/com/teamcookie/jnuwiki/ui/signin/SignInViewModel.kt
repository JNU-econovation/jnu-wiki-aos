package com.teamcookie.jnuwiki.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamcookie.jnuwiki.model.dto.RequestCheckEmailDTO
import com.teamcookie.jnuwiki.model.dto.RequestCheckNicknameDTO
import com.teamcookie.jnuwiki.model.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel(){
    private val repository = MainRepository
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
        if(uiState.value.pw != uiState.value.repeat){
            _uiState.update {
                it.copy(message = "비밀번호 재입력이 일치하지 않습니다.")
            }
        }
    }

    fun checkEmail(){
        viewModelScope.launch {
            val result = repository.checkEmail(RequestCheckEmailDTO(uiState.value.email))
            result.onFailure {Throw ->
                _uiState.update {
                    it.copy(message = Throw.message?: "Error")
                }
            }.onSuccess {info ->
                if(info.status == 200){
                    _uiState.update {
                        it.copy(isSignIn = true, message = "사용가능한 이메일입니다.")
                    }
                }else{
                    _uiState.update {
                        it.copy(message = info.)
                    }
                }
            }
        }
    }

    fun checkNickname(){
        viewModelScope.launch {
            val result = repository.checkNickname(RequestCheckNicknameDTO(uiState.value.nickname))
            result.onFailure {Throw ->
                _uiState.update {
                    it.copy(message = Throw.message?: "Error")
                }
            }.onSuccess {info ->
                if(info.status == 200){
                    _uiState.update {
                        it.copy(isSignIn = true, message = "사용가능한 닉네임입니다.")
                    }
                }else{
                    _uiState.update {
                        it.copy(message = it.message)
                    }
                }
            }
        }
    }
}