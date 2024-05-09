package com.mazer.bongobet.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mazer.bongobet.domain.entities.User
import com.mazer.bongobet.domain.repositories.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
): ViewModel() {

    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Initial)
    val loginState: StateFlow<LoginUiState> = _loginState

    fun handleEvent(event: Any){
        when (event){
            is LoginUiEvent.InitLoginGoogle -> {
                updateState(LoginUiState.LoadingGoogle)
            }
            is LoginUiEvent.InitLoginFacebook -> {
                updateState(LoginUiState.LoadingFacebook)
            }
            is LoginUiEvent.LoginGoogleSuccess -> {
                storeUserOnCache(event.user)
            }
        }
    }

    private fun storeUserOnCache(user: User){
        viewModelScope.launch {
            loginRepository.storeUserLoggedData(user)
            updateState(LoginUiState.LoginSuccess(user))
        }
    }

    private fun updateState(state: LoginUiState) {
        _loginState.update {
            state
        }
    }
}