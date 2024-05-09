package com.mazer.bongobet.ui.wallet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.mazer.bongobet.R
import com.mazer.bongobet.databinding.ActivityWalletBinding
import com.mazer.bongobet.domain.entities.Bet
import com.mazer.bongobet.domain.entities.BetType
import com.mazer.bongobet.domain.entities.UserBet
import com.mazer.bongobet.domain.entities.Wallet
import com.mazer.bongobet.domain.entities.pojo.BetHistoryResponse
import com.mazer.bongobet.ui.adapters.BetHistoryAdapter
import com.mazer.bongobet.ui.common.formatDate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WalletActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalletBinding
    private val viewModel : WalletViewModel by viewModel()
    private lateinit var adapterInProgress: BetHistoryAdapter
    private lateinit var adapterFinishedBets: BetHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        handleObservers()
    }

    private fun setupView() {
        setupAdapter()
        binding.btnDepositar.setOnClickListener {
            Snackbar.make(window.decorView, "Calma lá" , Snackbar.LENGTH_LONG).show()
            viewModel.handleEvent(WalletUiEvent.Depositar)
        }
        binding.btnSacar.setOnClickListener {
            Snackbar.make(window.decorView, "Jamais, não tem como!" , Snackbar.LENGTH_LONG).show()
            //viewModel.handleEvent(WalletUiEvent.Sacar)
        }
    }

    private fun handleObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.walletState.collectLatest {
                    handleWalletUiState(it)
                }
            }
        }
    }

    private fun setupWalletView(wallet: Wallet){
        binding.tvSaldo.text = getString(R.string.wallet_quantity, "%.2f".format(wallet.balance))
        binding.tvSaldo.visibility = View.VISIBLE
        if (wallet.valueOnBet > 0){
            binding.tvBetBlocked.visibility = View.VISIBLE
            binding.tvBetBlocked.text = getString(R.string.label_bet_blocked_quantity, "%.2f".format(wallet.valueOnBet))
        }else{
            binding.tvBetBlocked.visibility = View.GONE
        }

    }

    private fun handleWalletUiState(state: WalletUiState) {
        when (state){
            is WalletUiState.Initial -> {

            }
            is WalletUiState.Loading -> {}
            is WalletUiState.Completed -> {
            }
            is WalletUiState.WalletLoadedError -> {

            }
            is WalletUiState.WalletLoadedSuccess -> {
                setupWalletView(state.wallet)
            }
            WalletUiState.BetHistoryLoadedError -> {

            }
            is WalletUiState.BetHistoryLoadedSuccess -> {
                setupBetHistoryList(state.betHistoryList)
                setupBetFinishedHistoryList(state.betHistoryList)
                binding.loadingBetHistory.visibility = View.GONE

            }
        }
    }

    //TODO passar para um mapper
    private fun setupBetHistoryList(betHistoryList: List<BetHistoryResponse>) {
        val betsInProgress = betHistoryList.filter { betHistoryResponse ->
            betHistoryResponse.bets.any { it.statusBet == 0 }
        }

        if (betsInProgress.isNotEmpty()){
            binding.tvLabelEmAndamento.visibility = View.VISIBLE
            binding.betHistoryInProgress.visibility = View.VISIBLE
        }

        betsInProgress.forEachIndexed { indexBet, betHistoryResponse ->
            val idBet = indexBet + 1
            val betTitle = "Bet #${idBet} - ${betHistoryResponse.date.formatDate()}"

            adapterInProgress.addTitleToList(betTitle)
            betHistoryResponse.bets.forEachIndexed { indexBetType, bType ->
                val betType = BetType(
                    indexBetType+1,
                    bType.betType,
                    bType.condicao,
                    "",
                    bType.color,
                    bType.gametype,
                    bType.subDetails,
                    bType.odds
                )
                val userBet = UserBet(
                    idBet,
                    betType,
                    bType.quantity,
                    bType.statusBet
                )
                adapterInProgress.addToList(userBet)
            }
        }
    }

    private fun setupBetFinishedHistoryList(betHistoryList: List<BetHistoryResponse>) {

        val betsFinished = betHistoryList.filter { betHistoryResponse ->
            betHistoryResponse.bets.any { it.statusBet != 0 }
        }

        if (betsFinished.isNotEmpty()){
            binding.tvLabelFinalizadas.visibility = View.VISIBLE
            binding.betHistoryFinalizada.visibility = View.VISIBLE
        }

        betsFinished.forEachIndexed { indexBet, betHistoryResponse ->
            val idBet = indexBet + 1
            val betTitle = "Bet #${idBet} - ${betHistoryResponse.date.formatDate()}"

            adapterFinishedBets.addTitleToList(betTitle)
            betHistoryResponse.bets.forEachIndexed { indexBetType, bType ->
                val betType = BetType(
                    indexBetType+1,
                    bType.betType,
                    bType.condicao,
                    "",
                    bType.color,
                    bType.gametype,
                    bType.subDetails,
                    bType.odds
                )
                val userBet = UserBet(
                    idBet,
                    betType,
                    bType.quantity,
                    bType.statusBet
                )
                adapterFinishedBets.addToList(userBet)
            }
        }
    }

    private fun setupAdapter() {
        adapterInProgress = BetHistoryAdapter()
        adapterFinishedBets = BetHistoryAdapter()
        binding.betHistoryInProgress.setAdapter(adapterInProgress)
        binding.betHistoryFinalizada.setAdapter(adapterFinishedBets)
    }
}