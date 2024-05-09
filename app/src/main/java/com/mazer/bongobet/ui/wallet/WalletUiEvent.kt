package com.mazer.bongobet.ui.wallet

sealed class WalletUiEvent {
    object Depositar: WalletUiEvent()
    object Sacar: WalletUiEvent()
}