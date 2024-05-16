package com.mazer.bongobet.domain.entities

data class Streamer (
    val name: String,
    val channelId: String,
    val region: String,
    val rank: String,
    val tier: String,
    val pdl: Int,
    val thumbUrl: String,
    val started_at: String,
    val isOnline: Boolean,
    val isPlaying: Boolean,
    val gameNickName: String,
    val gameTag: String,
    val puuid: String
)