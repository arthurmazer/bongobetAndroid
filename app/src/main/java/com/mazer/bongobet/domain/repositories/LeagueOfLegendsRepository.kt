package com.mazer.bongobet.domain.repositories

import com.mazer.bongobet.common.utils.Result
import com.mazer.bongobet.domain.entities.BetType
import com.mazer.bongobet.domain.entities.GameType
import com.mazer.bongobet.domain.entities.LolUser
import com.mazer.bongobet.domain.entities.RiotAccount
import com.mazer.bongobet.domain.entities.pojo.BetHistoryResponse
import com.mazer.bongobet.domain.entities.pojo.BetResponse
import com.mazer.bongobet.domain.entities.pojo.MatchHistoryResponse
import kotlinx.coroutines.flow.Flow

interface LeagueOfLegendsRepository {

    fun getRiotAccountData(summonerName: String, tag: String): Flow<Result<RiotAccount>>
    fun searchSummonerData(puuid: String): Flow<Result<LolUser>>
    fun getMatchHistory(puuid:String, quantMatches: Int? = null): Flow<Result<MatchHistoryResponse>>
    fun getGameType(): Flow<Result<List<GameType>>>
    fun getBetsAvailable(gameTypeId: Int, puuid: String): Flow<Result<List<BetType>>>
    fun getBetsHistory(userEmail: String): Flow<Result<List<BetHistoryResponse>>>
    fun createBet(idBets: String, idQuantities: String, puuid: String, userEmail: String ): Flow<Result<BetResponse>>
}
