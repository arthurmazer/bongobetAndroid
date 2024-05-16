package com.mazer.bongobet.ui.bet.fragments

import com.mazer.bongobet.domain.entities.GameType
import com.mazer.bongobet.domain.entities.UserBet

sealed class BetUiEvent{
    data class ChangeGame(val gameType: GameType): BetUiEvent()
    data class LoadBetTypes(val gameTypeId: Int, val puuid: String): BetUiEvent()
    data class ConfirmBet(val betList: ArrayList<UserBet>, val puuid: String): BetUiEvent()
}
