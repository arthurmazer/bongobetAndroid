package com.mazer.bongobet.ui.home

import com.mazer.bongobet.domain.entities.RiotAccount

interface SummonerSearchUiState {

    object Initial : SummonerSearchUiState

    object Loading: SummonerSearchUiState

    object Completed : SummonerSearchUiState

    data class SummonerFound(val riotAccount: RiotAccount): SummonerSearchUiState

    data class SummonerNotFound(val msg: String): SummonerSearchUiState

    data class SearchError(val msg: String): SummonerSearchUiState

}