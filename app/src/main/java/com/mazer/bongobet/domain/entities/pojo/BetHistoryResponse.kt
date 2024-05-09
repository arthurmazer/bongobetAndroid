package com.mazer.bongobet.domain.entities.pojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class BetHistoryResponse(
    val date: String,
    val idUltimoJogo: String,
    val puuid: String,
    val bets: List<@RawValue Bet>
): Parcelable {
    @Parcelize
    data class Bet(
        val id: Int,
        val betType: String,
        val condicao: String,
        val color: String,
        val gametype: String,
        val statusBet: Int,
        val quantity: Float,
        val subDetails: String,
        val odds: Float
    ): Parcelable
}