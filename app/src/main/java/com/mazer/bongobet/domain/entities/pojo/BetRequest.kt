package com.mazer.bongobet.domain.entities.pojo

data class BetRequest (
    private val idBets: String,
    private val idQuantities: String,
    private val puuid: String,
    private val userEmail: String,
)