package com.teamcookie.jnuwiki.ui.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamcookie.jnuwiki.MainApplication
import com.teamcookie.jnuwiki.model.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class MapViewModel : ViewModel(){
    private val repository = MainRepository
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    fun checkLogin() {
        if(MainApplication.prefs.token == null){
            _uiState.update {
                it.copy(isLogin = false)
            }
        }else{
            viewModelScope.launch {
                var result = repository.getInfo() // 최초 시도

                if (result.isFailure) { // 통신 실패
                    _uiState.update {
                        it.copy(isLogin = false)
                    }
                    return@launch
                }

                val firstTryInfo = result.getOrThrow()
                if (firstTryInfo.result == SUCCESS) {
                    _uiState.update {
                        it.copy(isLogin = true, nickName = firstTryInfo.name) // 성공
                    }
                    return@launch
                } else if (firstTryInfo.result in listOf(NOT_FOUND, INTERNAL, BAD_REQ)) {
                    _uiState.update {
                        it.copy(isLogin = false) // 기타 실패
                    }
                    return@launch
                }

                //토큰 재발급
                MainApplication.prefs.token = null
                val tryIssueToken = repository.issueAccessToken()
                if(tryIssueToken.isFailure){ // 통신 실패
                    _uiState.update {
                        it.copy(isLogin = false)
                    }
                    return@launch
                }

                val issueInfo = tryIssueToken.getOrThrow()
                if(issueInfo == UNAUTH){
                    MainApplication.prefs.refresh = null // 갱신 실패
                    _uiState.update {
                        it.copy(isLogin = false)
                    }

                    return@launch
                }else if(issueInfo == INTERNAL){
                    _uiState.update {
                        it.copy(isLogin = false) // 기타 실패
                    }

                    return@launch
                }


                result = repository.getInfo() //두번째 시도

                if (result.isFailure) { // 통신 실패
                    _uiState.update {
                        it.copy(isLogin = false)
                    }
                    return@launch
                }

                val secondTryInfo = result.getOrThrow()
                if (secondTryInfo.result == SUCCESS) {
                    _uiState.update {
                        it.copy(isLogin = true, nickName = firstTryInfo.name) // 성공
                    }
                    return@launch
                } else if (firstTryInfo.result in listOf(NOT_FOUND, INTERNAL, BAD_REQ, UNAUTH)) { // 최종 실패
                    _uiState.update {
                        it.copy(isLogin = false)
                    }
                    return@launch
                }

            }

        }
    }

    companion object{
        const val SUCCESS = 200
        const val BAD_REQ = 400
        const val NOT_FOUND = 404
        const val UNAUTH = 401
        const val INTERNAL = 500
    }
}