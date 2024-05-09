package com.mazer.bongobet.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserBet (
    val idUserBet: Int,
    val betType: BetType,
    var quantity: Float,
    var statusBet: Int = 0
): Parcelable
