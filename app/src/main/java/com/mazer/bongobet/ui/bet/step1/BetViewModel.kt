package com.mazer.bongobet.ui.bet.step1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mazer.bongobet.domain.repositories.LoginRepository
import com.mazer.bongobet.domain.repositories.LeagueOfLegendsRepository
import com.mazer.bongobet.domain.usecases.BetUseCase
import com.mazer.bongobet.ui.common.toIdBetStringArray
import com.mazer.bongobet.ui.common.toQuantityBetStringArray
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BetViewModel(
    private val lolRepository: LeagueOfLegendsRepository,
    private val betUseCase: BetUseCase
): ViewModel() {

    private val _betTypeState = MutableStateFlow<BetUiState>(BetUiState.Step(1))
    val betTypeState: StateFlow<BetUiState> = _betTypeState

    init {
        getGameTypes()
    }

    private fun updateState(state: BetUiState) {
        _betTypeState.update {
            state
        }
    }

    private fun getBetTypes(idGameType: Int, puuid: String) {
        viewModelScope.launch {
            lolRepository.getBetsAvailable(idGameType, puuid).collectLatest {
                if (it.data != null) {
                    updateState(BetUiState.BetTypesLoadedSuccess(it.data))
                } else {
                    updateState(BetUiState.BetTypesLoadedError(it.message ?: "error game types not found!"))
                }
            }
        }
    }

    private fun getGameTypes(){
        viewModelScope.launch {
            lolRepository.getGameType().collectLatest {
                if(it.data != null){
                    updateState(BetUiState.GameTypeLoadedSuccess(it.data))
                }else{
                    updateState(BetUiState.GameTypeLoadedError(it.message ?: "error game types not found!"))
                }
            }
        }
    }

    fun handleEvents(event: BetUiEvent){
        when(event){
            is BetUiEvent.ChangeGame -> {
                updateState(BetUiState.GameTypeSelected)
            }
            is BetUiEvent.LoadBetTypes -> {
                updateState(BetUiState.BetTypesLoading)
                getBetTypes(event.gameTypeId, event.puuid)
            }
            is BetUiEvent.ConfirmBet -> {
                val stringBets = event.betList.toIdBetStringArray()
                val stringQuantity = event.betList.toQuantityBetStringArray()
                postBet(stringBets,stringQuantity,event.puuid)
            }
        }
    }

    private fun postBet(
        stringBets: String,
        stringQuantity: String,
        puuid: String
    ) {
        viewModelScope.launch {
            betUseCase.invoke(stringBets,stringQuantity,puuid).collectLatest {
                if(it.data != null){
                    updateState(BetUiState.BetCreatedSuccess(it.data))
                    updateState(BetUiState.Completed)
                }else{
                    updateState(BetUiState.GameTypeLoadedError(it.message ?: "Erro ao conectar ao servidor"))
                    updateState(BetUiState.Completed)
                }
            }
        }
    }
}