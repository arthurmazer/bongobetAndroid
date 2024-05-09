package com.mazer.bongobet.domain.entities

import com.google.gson.annotations.SerializedName

data class Wallet (
    @SerializedName("wallet_quantity")
    val balance: Float,
    @SerializedName("wallet_quantityBetLocked")
    val valueOnBet: Float
)