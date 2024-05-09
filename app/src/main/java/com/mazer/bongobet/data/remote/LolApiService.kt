package com.mazer.bongobet.data.remote

import com.mazer.bongobet.domain.entities.*
import com.mazer.bongobet.domain.entities.pojo.BetHistoryResponse
import com.mazer.bongobet.domain.entities.pojo.BetRequest
import com.mazer.bongobet.domain.entities.pojo.BetResponse
import com.mazer.bongobet.domain.entities.pojo.MatchHistoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LolApiService {

    @GET("get_riot_account/")
    suspend fun getRiotAccount(
        @Query("summoner_name") summonerName: String? = null,
        @Query("tag") tag: String? = null
    ): Response<RiotAccount>

    @GET("get_summoner_data/")
    suspend fun getSummonerData(
        @Query("puuid") puuid: String? = null
    ): Response<LolUser>

    @GET("get_match_history/")
    suspend fun getMatchHistory(
        @Query("puuid") puuid: String? = null,
        @Query("quant_matches") quantMatchs: Int? = null
    ): Response<MatchHistoryResponse>

    @GET("get_games_bet_type/")
    suspend fun getLolGameType(
    ): Response<List<GameType>>

    @GET("get_bet_type/")
    suspend fun getBetsAvailable(
        @Query("id") id: Int? = null,
        @Query("puuid") puuid: String? = null
    ): Response<List<BetType>>

    @POST("create_bet/")
    suspend fun createBet(
        @Body betRequest: BetRequest
    ): Response<BetResponse>

    @GET("get_bet_history/")
    suspend fun getBetHistory(
        @Query("user_email") userEmail: String? = null
    ): Response<List<BetHistoryResponse>>
}