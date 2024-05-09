package com.mazer.bongobet.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BetType (
    val id: Int,
    val name: String,
    val condicao: String,
    val urlThumb: String,
    val color: String,
    val gametype: String,
    val subDetails: String,
    val odds: Float
): Parcelable
