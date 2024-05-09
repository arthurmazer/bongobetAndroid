package com.mazer.bongobet.ui.lolprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mazer.bongobet.domain.repositories.LeagueOfLegendsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LolProfileViewModel(
    private val lolRepository: LeagueOfLegendsRepository
) : ViewModel() {

    private val _lolProfileState = MutableStateFlow<LolProfileUiState>(LolProfileUiState.Loading)
    val lolProfileState: StateFlow<LolProfileUiState> = _lolProfileState

    private fun getMatchHistory(puuid: String){
        viewModelScope.launch {
            lolRepository.getMatchHistory(puuid).collectLatest { result ->
                if (result.data != null){
                    updateState(LolProfileUiState.MatchHistorySucess(result.data))
                }else{
                    updateState(LolProfileUiState.MatchHistoryError(result.message ?: "Summoner não encontrado!"))
                }
            }
        }
    }


    fun finishedEvent() = updateState(state = LolProfileUiState.Completed)

    private fun getSummonerData(puuid: String){
        viewModelScope.launch {
            lolRepository.searchSummonerData(puuid).collectLatest {
                if(it.data != null){
                    updateState(LolProfileUiState.SummonerDataLoadedSuccess(it.data))
                }else{
                    updateState(LolProfileUiState.SummonerDataLoadedError("error user not found!"))
                }
            }
        }
    }

    private fun updateState(state: LolProfileUiState) {
        _lolProfileState.update {
            state
        }
    }

    fun handleEvent(event: LolProfileUiEvent){
        when(event) {
            is LolProfileUiEvent.GetLolProfile -> {
                updateState(LolProfileUiState.Loading)
                getSummonerData(event.puuid)
                getMatchHistory(event.puuid)
            }
        }
    }
}