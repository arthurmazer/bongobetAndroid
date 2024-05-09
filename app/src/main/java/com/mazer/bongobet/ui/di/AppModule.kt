package com.mazer.bongobet.ui.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.mazer.bongobet.BuildConfig
import com.mazer.bongobet.data.local.CacheDataSource
import com.mazer.bongobet.data.local.CacheDataSourceImpl
import com.mazer.bongobet.data.remote.LolApiService
import com.mazer.bongobet.data.remote.WalletService
import com.mazer.bongobet.data.repos.LeagueOfLegendsRepositoryImpl
import com.mazer.bongobet.data.repos.LoginRepositoryImpl
import com.mazer.bongobet.data.repos.WalletRepositoryImpl
import com.mazer.bongobet.domain.repositories.LeagueOfLegendsRepository
import com.mazer.bongobet.domain.repositories.LoginRepository
import com.mazer.bongobet.domain.repositories.WalletRepository
import com.mazer.bongobet.domain.usecases.*
import com.mazer.bongobet.ui.MainViewModel
import com.mazer.bongobet.ui.bet.step1.BetViewModel
import com.mazer.bongobet.ui.bet.step4.BetSuccessViewModel
import com.mazer.bongobet.ui.home.SummonerSearchViewModel
import com.mazer.bongobet.ui.login.LoginViewModel
import com.mazer.bongobet.ui.lolprofile.LolProfileViewModel
import com.mazer.bongobet.ui.wallet.WalletViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {


    single<SharedPreferences> {
        androidContext().getSharedPreferences("bongo_bet_prefs", Context.MODE_PRIVATE)
    }

    single {
        val loggingInterceptor = HttpLoggingInterceptor{ message -> Log.d("HttpLoggingInterceptor", message) }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder()
            .addNetworkInterceptor (loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .build()
                chain.proceed(request)
            }
            .build()
    }


    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<LolApiService> {
        get<Retrofit>().create(LolApiService::class.java)
    }
    single<WalletService> {
        get<Retrofit>().create(WalletService::class.java)
    }


    factory<CacheDataSource> { CacheDataSourceImpl(get()) }
    factory<LoginRepository> { LoginRepositoryImpl(get()) }
    factory<LeagueOfLegendsRepository> { LeagueOfLegendsRepositoryImpl(get()) }
    factory<WalletRepository> { WalletRepositoryImpl(get()) }
    factory<BetUseCase> { BetUseCaseImpl(get(), get())}
    factory<GetWalletUseCase> { GetWalletUseCaseImpl(get(), get())}
    factory<GetBetHistoryUseCase> { GetBetHistoryUseCaseImpl(get(),get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { LolProfileViewModel(get())}
    viewModel { MainViewModel(get(), get()) }
    viewModel { SummonerSearchViewModel(get())}
    viewModel { BetViewModel(get(), get()) }
    viewModel { BetSuccessViewModel(get()) }
    viewModel { WalletViewModel(get(), get()) }

}
