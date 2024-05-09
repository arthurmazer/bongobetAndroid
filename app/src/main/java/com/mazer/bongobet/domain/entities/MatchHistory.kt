package com.mazer.bongobet.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class MatchHistory(
    val gameId: String,
    val gameType: String,
    @SerializedName("time_string")
    val timeString: String,
    @SerializedName("duration_string")
    val durationString: String,
    val result: String,
    val championId: Int,
    val championName: String,
    val championLevel: Int,
    val championImg: String,
    val kda: String,
    val kills: Int,
    val assists: Int,
    val deaths: Int,
    val kdaRation: Float,
    val dragonKills: Int,
    val firstBloodKill: Boolean,
    val firstTowerKill: Boolean,
    val multikills: Int,
    val teamBaronKills: Int,
    val soloKills: Int,
    val teamElderDragonKills: Int,
    val teamRiftHeraldKills: Int,
    val killingSprees: Int,
    val item0: String,
    val item1: String,
    val item2: String,
    val item3: String,
    val item4: String,
    val item5: String,
    val trinket: String,
    val lanePlayed: String,
    val teams: List<@RawValue TeamPlayer>,
    val controlWardsPlaced: Int,
    val totalMinionsKilled: Int,
    val win: Boolean
): Parcelable {

    @Parcelize
    data class TeamPlayer(
        val playerName: String,
        val champImg: String,
        val championPlayed: String
    ): Parcelable
}