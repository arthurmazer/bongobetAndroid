package com.mazer.bongobet.ui.lolprofile

import com.mazer.bongobet.domain.entities.LolUser
import com.mazer.bongobet.domain.entities.pojo.MatchHistoryResponse

interface LolProfileUiState {

    object Loading: LolProfileUiState

    object Completed: LolProfileUiState

    data class MatchHistorySucess(val listMatch: MatchHistoryResponse): LolProfileUiState

    data class MatchHistoryError(val msg: String): LolProfileUiState

    data class SummonerDataLoadedSuccess(val lolUser: LolUser): LolProfileUiState

    data class SummonerDataLoadedError(val msg: String): LolProfileUiState
}