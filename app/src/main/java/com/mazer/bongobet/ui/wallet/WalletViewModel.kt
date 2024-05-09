package com.mazer.bongobet.ui.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mazer.bongobet.domain.repositories.WalletRepository
import com.mazer.bongobet.domain.usecases.GetBetHistoryUseCase
import com.mazer.bongobet.domain.usecases.GetWalletUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WalletViewModel(
    private val getWalletUseCase: GetWalletUseCase,
    private val getBetHistoryUseCase: GetBetHistoryUseCase
): ViewModel() {

    private val _walletState = MutableStateFlow<WalletUiState>(WalletUiState.Initial)
    val walletState: StateFlow<WalletUiState> = _walletState

    init {
        checkUserWallet()
        checkBetHistory()
    }

    private fun checkUserWallet(){
        viewModelScope.launch {
            getWalletUseCase.invoke().collectLatest { result ->
                if (result.data != null){
                    updateState(WalletUiState.WalletLoadedSuccess(result.data))
                    finishEvent()
                }else{
                    updateState(WalletUiState.WalletLoadedError)
                    finishEvent()
                }
            }
        }
    }

    private fun checkBetHistory(){
        viewModelScope.launch {
            getBetHistoryUseCase.invoke().collectLatest { result ->

                if (result.data != null){
                    updateState(WalletUiState.BetHistoryLoadedSuccess(result.data))
                    finishEvent()
                }else{
                    updateState(WalletUiState.BetHistoryLoadedError)
                    finishEvent()
                }
            }
        }
    }

    private fun finishEvent() = updateState(state = WalletUiState.Completed)

    private fun updateState(state: WalletUiState) {
        _walletState.update {
            state
        }
    }

    fun handleEvent(event: WalletUiEvent){
        when(event) {
            is WalletUiEvent.Sacar -> {
            }
            is WalletUiEvent.Depositar -> {
            }
        }
    }
}