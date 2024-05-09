package com.mazer.bongobet.ui

import com.mazer.bongobet.domain.entities.User
import com.mazer.bongobet.domain.entities.Wallet

interface MainUiState {

    object Initial : MainUiState

    object UserNotLoggedIn : MainUiState

    data class UserLoggedIn(val user: User): MainUiState

    data class WalletLoadedSuccess(val wallet: Wallet): MainUiState
    object WalletLoadedError: MainUiState



}