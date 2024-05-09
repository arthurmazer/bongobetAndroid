package com.mazer.bongobet.domain.repositories

import com.mazer.bongobet.common.utils.Result
import com.mazer.bongobet.domain.entities.Wallet
import kotlinx.coroutines.flow.Flow

interface WalletRepository {

    fun getWallet(userEmail: String): Flow<Result<Wallet>>
}