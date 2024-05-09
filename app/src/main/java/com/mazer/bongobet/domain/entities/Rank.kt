package com.mazer.bongobet.domain.entities

data class Rank (
    var puuid: String = "",
    val queueType: String = "",
    val tier: String = "",
    val rank: String = "",
    val pdl: String = "",
    val wins: Int = 0,
    val losses: Int = 0,
    val iconRankSolo: String = ""
)