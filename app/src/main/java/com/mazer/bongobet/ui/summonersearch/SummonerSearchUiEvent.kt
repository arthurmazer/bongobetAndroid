package com.mazer.bongobet.ui.summonersearch

sealed class SummonerSearchUiEvent {

    data class SearchLoLSummonerData(val nickAndTag: String) : SummonerSearchUiEvent()

}
