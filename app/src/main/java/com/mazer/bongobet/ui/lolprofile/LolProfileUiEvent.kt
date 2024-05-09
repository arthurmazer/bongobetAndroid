package com.mazer.bongobet.ui.lolprofile

sealed class LolProfileUiEvent {
    data class GetLolProfile(val puuid: String, val summonerName: String) : LolProfileUiEvent()
}
