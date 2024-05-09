package com.mazer.bongobet.domain.usecases

import com.mazer.bongobet.common.utils.Result
import com.mazer.bongobet.domain.entities.pojo.BetHistoryResponse
import com.mazer.bongobet.domain.repositories.LeagueOfLegendsRepository
import com.mazer.bongobet.domain.repositories.LoginRepository
import kotlinx.coroutines.flow.Flow

class GetBetHistoryUseCaseImpl(
    private val lolRepository: LeagueOfLegendsRepository,
    private val loginRepository: LoginRepository
): GetBetHistoryUseCase {
    override fun invoke(): Flow<Result<List<BetHistoryResponse>>>{
        val user = loginRepository.getUserLoggedData()

        return lolRepository.getBetsHistory(user.email)
    }
}