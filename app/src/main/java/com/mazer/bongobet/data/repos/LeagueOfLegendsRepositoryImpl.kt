package com.mazer.bongobet.data.repos

import com.mazer.bongobet.common.utils.Result
import com.mazer.bongobet.data.remote.LolApiService
import com.mazer.bongobet.domain.entities.*
import com.mazer.bongobet.domain.entities.pojo.BetHistoryResponse
import com.mazer.bongobet.domain.entities.pojo.BetRequest
import com.mazer.bongobet.domain.entities.pojo.BetResponse
import com.mazer.bongobet.domain.entities.pojo.MatchHistoryResponse
import com.mazer.bongobet.domain.repositories.LeagueOfLegendsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow

class LeagueOfLegendsRepositoryImpl(
    private val lolApiService: LolApiService
): LeagueOfLegendsRepository {

    override fun getRiotAccountData(summonerName: String, tag: String): Flow<Result<RiotAccount>> {
        return channelFlow {
            val response = lolApiService.getRiotAccount(summonerName, tag)
            val riotAccount = response.body()
            if (response.isSuccessful && riotAccount != null) {
                send(Result.Sucess(riotAccount))
            } else {
                send(Result.Error(response.message()))
            }

        }.catch {
           //send(Result.Error(it.message))
        }
    }

    override fun searchSummonerData(puuid: String): Flow<Result<LolUser>> {
        return flow {
            val response = lolApiService.getSummonerData(puuid)
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

    override fun getMatchHistory(
        puuid: String,
        quantMatches: Int?
    ): Flow<Result<MatchHistoryResponse>> {
        return flow{
            val response = lolApiService.getMatchHistory(puuid, quantMatches)
            val matchHistory = response.body()
            if (response.isSuccessful && matchHistory != null){
                emit(Result.Sucess(matchHistory))
            }else{
                emit(Result.Error(response.message()))
            }
        }.catch {
            emit(Result.Error(it.message))
        }
    }

    override fun getGameType(): Flow<Result<List<GameType>>> {
        return flow{
            val response = lolApiService.getLolGameType()
            val gameTypes = response.body()
            if (response.isSuccessful && gameTypes != null){
                emit(Result.Sucess(gameTypes))
            }else{
                emit(Result.Error(response.message()))
            }
        }.catch {
            emit(Result.Error(it.message))
        }
    }

    override fun getBetsAvailable(gameTypeId: Int, puuid: String): Flow<Result<List<BetType>>> {
        return flow{
            val response = lolApiService.getBetsAvailable(gameTypeId,puuid)
            val betTypes = response.body()
            if (response.isSuccessful && betTypes != null){
                emit(Result.Sucess(betTypes))
            }else{
                emit(Result.Error(response.message()))
            }
        }.catch {
            emit(Result.Error(it.message))
        }
    }

    override fun getBetsHistory(userEmail: String): Flow<Result<List<BetHistoryResponse>>> {
        return flow{
            val response = lolApiService.getBetHistory(userEmail)
            val betHistory = response.body()
            if (response.isSuccessful && betHistory != null){
                emit(Result.Sucess(betHistory))
            }else{
                emit(Result.Error(response.message()))
            }
        }.catch {
            emit(Result.Error(it.message))
        }
    }

    override fun createBet(
        idBets: String,
        idQuantities: String,
        puuid: String,
        userEmail: String
    ): Flow<Result<BetResponse>> {
        return flow{
            val betRequest = BetRequest(idBets, idQuantities, puuid, userEmail)
            val response = lolApiService.createBet(betRequest)
            val betTypes = response.body()
            if (response.isSuccessful && betTypes != null){
                emit(Result.Sucess(betTypes))
            }else{
                emit(Result.Error(response.message()))
            }
        }.catch {
            emit(Result.Error(it.message))
        }
    }

    override fun getStreamersTwitchList(): Flow<Result<List<Streamer>>> {
        return flow{
            val response = lolApiService.getStreamersTwitchList()
            val streamersList = response.body()
            if (response.isSuccessful && streamersList != null){
                emit(Result.Sucess(streamersList))
            }else{
                emit(Result.Error(response.message()))
            }
        }.catch {
            emit(Result.Error(it.message))
        }
    }

}



