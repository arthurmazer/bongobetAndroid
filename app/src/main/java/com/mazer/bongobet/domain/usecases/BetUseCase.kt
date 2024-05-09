package com.mazer.bongobet.domain.usecases

import com.mazer.bongobet.domain.entities.pojo.BetResponse
import kotlinx.coroutines.flow.Flow
import com.mazer.bongobet.common.utils.Result

interface BetUseCase {
    fun invoke(stringBets: String, stringQuantitys: String, puuid: String): Flow<Result<BetResponse>>
}