package com.mazer.bongobet.ui.login

import com.mazer.bongobet.domain.entities.User

sealed interface LoginUiState {
    object Initial : LoginUiState

    object LoadingGoogle : LoginUiState
    object LoadingFacebook : LoginUiState

    data class LoginSuccess(val user: User) : LoginUiState

    data class LoginError(val msg: String): LoginUiState
}