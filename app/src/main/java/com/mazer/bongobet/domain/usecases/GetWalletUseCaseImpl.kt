package com.mazer.bongobet.domain.usecases

import com.mazer.bongobet.common.utils.Result
import com.mazer.bongobet.domain.entities.Wallet
import com.mazer.bongobet.domain.repositories.LoginRepository
import com.mazer.bongobet.domain.repositories.WalletRepository
import kotlinx.coroutines.flow.Flow

class GetWalletUseCaseImpl(
    private val walletRepository: WalletRepository,
    private val loginRepository: LoginRepository
): GetWalletUseCase {
    override fun invoke(): Flow<Result<Wallet>> {
        val user = loginRepository.getUserLoggedData()

        return walletRepository.getWallet(
            userEmail = user.email
        )
    }
}