package com.mazer.bongobet.data.remote

import com.mazer.bongobet.domain.entities.Wallet
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WalletService {

    @GET("get_wallet/")
    suspend fun getWalletData(
        @Query("user_email") userEmail: String? = null
    ): Response<Wallet>
}