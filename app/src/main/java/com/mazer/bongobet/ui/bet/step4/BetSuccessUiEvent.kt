package com.mazer.bongobet.ui.bet.step4

import BetSuccessUiState
import com.mazer.bongobet.domain.entities.GameType
import com.mazer.bongobet.domain.entities.UserBet

sealed class BetSuccessUiEvent{
    object FinishBet : BetSuccessUiEvent()
    data class GetLastMatch(val puuid: String): BetSuccessUiEvent()
}
