package com.mazer.bongobet.ui.home

sealed class SummonerSearchUiEvent {

    data class SearchLoLSummonerData(val nickAndTag: String) : SummonerSearchUiEvent()

}
