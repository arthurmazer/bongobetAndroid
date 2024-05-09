package com.mazer.bongobet.domain.usecases

import com.mazer.bongobet.common.utils.Result
import com.mazer.bongobet.domain.entities.pojo.BetHistoryResponse
import kotlinx.coroutines.flow.Flow

interface GetBetHistoryUseCase {
    fun invoke(): Flow<Result<List<BetHistoryResponse>>>
}