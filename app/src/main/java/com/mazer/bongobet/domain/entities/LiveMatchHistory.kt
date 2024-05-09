package com.mazer.bongobet.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class LiveMatchHistory(
    val queueId: Int,
    val gameType: String,
    val gamestartTime: String,
    val gameLength: Int,
    val gameId: String,
    val championPlayed: String,
    val participants: List<@RawValue TeamPlayer>
): Parcelable {
    @Parcelize
    data class TeamPlayer(
        val summonerName: String,
        val teamId: Int,
        val championId: Int,
        val championName: String,
        val championImg: String
    ): Parcelable
}