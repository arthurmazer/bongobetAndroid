package com.mazer.bongobet.domain.entities

data class Bet (
    private val date: String,
    private val statusBet: Int,
    private val idUltimoJogo: String,
    private val puuid: String
)