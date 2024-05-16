package com.mazer.bongobet.ui.bet.response

sealed class BetSuccessUiEvent{
    object FinishBet : BetSuccessUiEvent()
    data class GetLastMatch(val puuid: String): BetSuccessUiEvent()
}
