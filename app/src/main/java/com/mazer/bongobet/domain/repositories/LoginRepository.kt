package com.mazer.bongobet.domain.repositories

import com.mazer.bongobet.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun storeUserLoggedData(user: User)
    fun getUserLoggedData() : User
}