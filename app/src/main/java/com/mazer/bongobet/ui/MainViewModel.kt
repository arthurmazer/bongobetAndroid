package com.mazer.bongobet.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mazer.bongobet.domain.repositories.LoginRepository
import com.mazer.bongobet.domain.repositories.WalletRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val loginRepository: LoginRepository,
    private val walletRepository: WalletRepository
): ViewModel() {

    private val _mainState = MutableStateFlow<MainUiState>(MainUiState.Initial)
    val mainState: StateFlow<MainUiState> = _mainState


    init {
        checkUserLoggedIn()
    }

    fun checkUserLoggedIn(){
        viewModelScope.launch {
            val user = loginRepository.getUserLoggedData()
            if (user.email.isBlank()){
                updateState(MainUiState.UserNotLoggedIn)
            }else{
                updateState(MainUiState.UserLoggedIn(user))
                checkUserWallet(user.email)
            }
        }
    }

    private fun checkUserWallet(userEmail: String){
        viewModelScope.launch {
            walletRepository.getWallet(userEmail).collectLatest { result ->
                if (result.data != null){
                    updateState(MainUiState.WalletLoadedSuccess(result.data))
                }else{
                    updateState(MainUiState.WalletLoadedError)
                }
            }
        }
    }

    private fun updateState(state: MainUiState) {
        _mainState.update {
            state
        }
    }
}