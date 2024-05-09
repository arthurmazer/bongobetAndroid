package com.mazer.bongobet.data.repos

import com.mazer.bongobet.common.utils.Result
import com.mazer.bongobet.data.remote.WalletService
import com.mazer.bongobet.domain.entities.Wallet
import com.mazer.bongobet.domain.repositories.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class WalletRepositoryImpl(
    private val walletService: WalletService
): WalletRepository {
    override fun getWallet(userEmail: String): Flow<Result<Wallet>> {
        return flow {
            val response = walletService.getWalletData(userEmail)
            val lolProfile = response.body()
            if (response.isSuccessful && lolProfile != null) {
                emit(Result.Sucess(lolProfile))
            } else {
                emit(Result.Error(response.message()))
            }
        }.catch {
            emit(Result.Error(it.message))
        }
    }
}