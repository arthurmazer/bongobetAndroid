package com.mazer.bongobet.ui.wallet

import com.mazer.bongobet.domain.entities.Wallet
import com.mazer.bongobet.domain.entities.pojo.BetHistoryResponse

sealed interface WalletUiState {
    object Initial: WalletUiState
    object Loading: WalletUiState
    object Completed: WalletUiState
    data class WalletLoadedSuccess(val wallet: Wallet): WalletUiState
    object WalletLoadedError: WalletUiState
    data class BetHistoryLoadedSuccess(val betHistoryList: List<BetHistoryResponse>): WalletUiState
    object BetHistoryLoadedError: WalletUiState

}