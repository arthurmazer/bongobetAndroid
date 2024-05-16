package com.mazer.bongobet.ui.home

import com.mazer.bongobet.domain.entities.RiotAccount
import com.mazer.bongobet.domain.entities.Streamer

interface SummonerSearchUiState {

    object Initial : SummonerSearchUiState

    object Loading: SummonerSearchUiState

    object Completed : SummonerSearchUiState

    data class SummonerFound(val riotAccount: RiotAccount): SummonerSearchUiState

    data class SummonerNotFound(val msg: String): SummonerSearchUiState

    data class SearchError(val msg: String): SummonerSearchUiState

    data class StreamerListFound(val streamerList: List<Streamer>): SummonerSearchUiState

    data class StreamerListNotFound(val msg: String): SummonerSearchUiState


}