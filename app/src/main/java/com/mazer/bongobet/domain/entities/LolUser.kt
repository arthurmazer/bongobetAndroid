package com.mazer.bongobet.domain.entities

data class LolUser (
        val puuid: String = "",
        val id: String = "",
        val accountId: String = "",
        val urlProfile: String = "",
        val summonerLevel: Int = 0,
){
        val ranks: List<Rank> = listOf()
}