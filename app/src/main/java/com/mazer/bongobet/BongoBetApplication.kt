package com.mazer.bongobet

import android.app.Application
import com.google.firebase.FirebaseApp
import com.mazer.bongobet.ui.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class BongoBetApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@BongoBetApplication)
            modules(appModule)
        }
    }
}
