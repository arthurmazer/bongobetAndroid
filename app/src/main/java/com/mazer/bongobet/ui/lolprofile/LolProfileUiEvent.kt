package com.mazer.bongobet.ui.lolprofile

sealed class LolProfileUiEvent {
    data class GetLolProfile(val puuid: String) : LolProfileUiEvent()
}
