package com.mazer.bongobet.domain.usecases

import com.mazer.bongobet.common.utils.Result
import com.mazer.bongobet.domain.entities.Wallet
import kotlinx.coroutines.flow.Flow

interface GetWalletUseCase {
    fun invoke(): Flow<Result<Wallet>>
}