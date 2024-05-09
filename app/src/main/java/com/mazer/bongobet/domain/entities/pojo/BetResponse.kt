package com.mazer.bongobet.domain.entities.pojo

data class BetResponse (
    private val code: Int,
    private val userEmail: String,
    private val lastMatchId: String,
    private val totalValue: Float
)