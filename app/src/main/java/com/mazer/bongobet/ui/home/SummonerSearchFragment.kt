package com.mazer.bongobet.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.mazer.bongobet.R
import com.mazer.bongobet.databinding.FragmentSummonerSearchBinding
import com.mazer.bongobet.domain.entities.RiotAccount
import com.mazer.bongobet.domain.entities.User
import com.mazer.bongobet.domain.entities.Wallet
import com.mazer.bongobet.ui.MainUiState
import com.mazer.bongobet.ui.MainViewModel
import com.mazer.bongobet.ui.bet.step4.BetSuccessFragmentDirections
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SummonerSearchFragment: Fragment() {

    private lateinit var binding: FragmentSummonerSearchBinding
    private val viewModel: SummonerSearchViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSummonerSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        handleObservers()
    }


    private fun setupView() {
        binding.tvButtonSearch.setOnClickListener {
            viewModel.handleEvent(SummonerSearchUiEvent.SearchLoLSummonerData(binding.etSummonerName.text.toString()))
        }
    }

    private fun goToProfileScreen(summoner: RiotAccount){
        val action = SummonerSearchFragmentDirections.actionSummonerSearchToLolProfile(summoner.gameName, summoner.puuid)
        findNavController().navigate(action)
    }

    private fun handleObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.summonerSearchState.collectLatest {
                        handleLolProfileUiState(it)
                    }
                }
            }
        }
    }



    private fun showSnackBarMessage(message: String){
        Snackbar.make(this.view ?: return, message, Snackbar.LENGTH_LONG).show()
    }

    private fun handleLolProfileUiState(state: SummonerSearchUiState) {
        when (state) {
            is SummonerSearchUiState.SummonerFound -> {
                showLoading(false)
                goToProfileScreen(state.riotAccount)
                viewModel.finishedEvent()
            }
            is SummonerSearchUiState.SearchError -> {
                showLoading(false)
                showSnackBarMessage(state.msg)
                viewModel.finishedEvent()
            }
            is SummonerSearchUiState.SummonerNotFound -> {
                showLoading(false)
                showSnackBarMessage(state.msg)
                viewModel.finishedEvent()
            }

            is SummonerSearchUiState.Loading -> {
                showLoading(true)
            }
            is SummonerSearchUiState.Completed -> {
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.loadingSummoner.visibility = View.VISIBLE
        }else{
            binding.loadingSummoner.visibility = View.GONE
        }
    }


}