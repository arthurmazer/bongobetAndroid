package com.mazer.bongobet.data.local

import android.content.SharedPreferences

class CacheDataSourceImpl(private val sharedPreferences: SharedPreferences): CacheDataSource {
    override fun saveCacheData(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun loadCacheData(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
}