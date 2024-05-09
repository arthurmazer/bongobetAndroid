package com.mazer.bongobet.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mazer.bongobet.domain.repositories.LeagueOfLegendsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SummonerSearchViewModel(
    private val lolRepository: LeagueOfLegendsRepository
): ViewModel() {

    private val _summonerSearchState = MutableStateFlow<SummonerSearchUiState>(SummonerSearchUiState.Initial)
    val summonerSearchState: StateFlow<SummonerSearchUiState> = _summonerSearchState

    private fun getRiotAccount(summonerName: String, tag: String){
        viewModelScope.launch {
            lolRepository.getRiotAccountData(summonerName,tag).collectLatest { result ->
                if (result.data != null){
                    updateState(SummonerSearchUiState.SummonerFound(result.data))
                }else{
                    updateState(SummonerSearchUiState.SummonerNotFound(result.message ?: "Summoner não encontrado!"))
                }
            }
        }
    }

    private fun updateState(state: SummonerSearchUiState) {
        _summonerSearchState.update {
            state
        }
    }

    fun finishedEvent() = updateState(state = SummonerSearchUiState.Completed)

    fun handleEvent(event: SummonerSearchUiEvent){
        when(event) {
            is SummonerSearchUiEvent.SearchLoLSummonerData -> {
                updateState(SummonerSearchUiState.Loading)
                val summoner = event.nickAndTag.split("#", limit = 2)
                if (summoner.size != 2){
                    updateState(SummonerSearchUiState.SearchError("Deve inserir seu summonerName + #TAG"))
                }else{
                    getRiotAccount(summoner[0], summoner[1])
                }
            }
        }
    }

}