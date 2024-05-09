package com.mazer.bongobet.ui.bet.step1

import com.mazer.bongobet.domain.entities.pojo.BetResponse
import com.mazer.bongobet.domain.entities.BetType
import com.mazer.bongobet.domain.entities.GameType

interface BetUiState {

    data class Step(val step: Int): BetUiState
    object Loading: BetUiState
    object Completed: BetUiState
    data class  GameTypeLoadedSuccess(val gameTypeList: List<GameType>): BetUiState
    data class  GameTypeLoadedError(val msg: String): BetUiState
    object GameTypeSelected: BetUiState
    object BetTypesLoading: BetUiState
    data class BetTypesLoadedSuccess(val betList : List<BetType>): BetUiState
    data class BetTypesLoadedError(val msg: String): BetUiState
    data class BetCreatedSuccess(val betResponse: BetResponse): BetUiState

}