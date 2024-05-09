package com.mazer.bongobet.domain.usecases

import com.mazer.bongobet.common.utils.Result
import com.mazer.bongobet.domain.entities.pojo.BetResponse
import com.mazer.bongobet.domain.repositories.LeagueOfLegendsRepository
import com.mazer.bongobet.domain.repositories.LoginRepository
import kotlinx.coroutines.flow.Flow

class BetUseCaseImpl(
    private val lolRepository: LeagueOfLegendsRepository,
    private val loginRepository: LoginRepository
): BetUseCase {
    override fun invoke(
        stringBets: String,
        stringQuantitys: String,
        puuid: String
    ): Flow<Result<BetResponse>> {
        val user = loginRepository.getUserLoggedData()

        return lolRepository.createBet(
                    idBets = stringBets,
                    idQuantities = stringQuantitys,
                    puuid = puuid,
                    userEmail = user.email
                )
    }
}