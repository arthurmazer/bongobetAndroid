package com.mazer.bongobet.data.repos

import com.mazer.bongobet.data.local.CacheDataSource
import com.mazer.bongobet.data.local.SharedPrefConsts
import com.mazer.bongobet.domain.entities.User
import com.mazer.bongobet.domain.repositories.LoginRepository

class LoginRepositoryImpl(
    private val cacheDataSource: CacheDataSource
): LoginRepository {


    override suspend fun storeUserLoggedData(user: User) {
        cacheDataSource.saveCacheData(SharedPrefConsts.USER_DISPLAY_NAME, user.displayName)
        cacheDataSource.saveCacheData(SharedPrefConsts.USER_EMAIL, user.email)
        cacheDataSource.saveCacheData(SharedPrefConsts.USER_PHOTO_URI, user.profileUri)
    }

    override fun getUserLoggedData(): User {
        return User(
            cacheDataSource.loadCacheData(SharedPrefConsts.USER_DISPLAY_NAME, ""),
            cacheDataSource.loadCacheData(SharedPrefConsts.USER_EMAIL, ""),
            cacheDataSource.loadCacheData(SharedPrefConsts.USER_PHOTO_URI, "")
        )
    }

}