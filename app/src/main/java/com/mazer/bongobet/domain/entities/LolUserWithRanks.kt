package com.mazer.bongobet.domain.entities

data class LolUserWithRanks (
    val lolUser: LolUser,
    val ranks: List<Rank>
)