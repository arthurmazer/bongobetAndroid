package com.mazer.bongobet.domain.repositories

interface LolProfileRepository {
    suspend fun getMatchHistory(summonerName: String, puuid: String)
}