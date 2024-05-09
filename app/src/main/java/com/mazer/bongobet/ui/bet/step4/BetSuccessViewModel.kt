package com.mazer.bongobet.ui.bet.step4

import BetSuccessUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mazer.bongobet.domain.repositories.LeagueOfLegendsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BetSuccessViewModel(
    private val lolRepository: LeagueOfLegendsRepository
): ViewModel() {

    private val _lolBetSuccessState = MutableStateFlow<BetSuccessUiState>(BetSuccessUiState.Initial)
    val lolBetSuccessState: StateFlow<BetSuccessUiState> = _lolBetSuccessState

    private fun getMatchHistory(
        puuid: String
    ) {
        viewModelScope.launch {
            lolRepository.getMatchHistory(puuid, 1).collectLatest { result ->
                if (result.data != null && result.data.summoners_rift.isNotEmpty()){
                    updateState(BetSuccessUiState.GetLastMatchSuccess(result.data))
                }else{
                    updateState(BetSuccessUiState.GetLastMatchError(result.message ?: "Summoner não encontrado!"))
                }
            }
        }
    }

    private fun updateState(state: BetSuccessUiState) {
        _lolBetSuccessState.update {
            state
        }
    }

    fun handleEvent(event: BetSuccessUiEvent){
        when(event) {
            is BetSuccessUiEvent.FinishBet -> {
            }
            is BetSuccessUiEvent.GetLastMatch -> {
                getMatchHistory(event.puuid)
            }
        }
    }
}