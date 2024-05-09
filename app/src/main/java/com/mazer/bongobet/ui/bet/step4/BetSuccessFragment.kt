package com.mazer.bongobet.ui.bet.step4

import BetSuccessUiState
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mazer.bongobet.R
import com.mazer.bongobet.databinding.FragmentBetSucessoBinding
import com.mazer.bongobet.domain.entities.LiveMatchHistory
import com.mazer.bongobet.domain.entities.MatchHistory
import com.mazer.bongobet.domain.entities.pojo.MatchHistoryResponse
import com.mazer.bongobet.ui.MainUiState
import com.mazer.bongobet.ui.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BetSuccessFragment: Fragment() {

    private lateinit var binding: FragmentBetSucessoBinding
    private val viewModel : BetSuccessViewModel by viewModel()
    private val args: BetSuccessFragmentArgs by navArgs()
    private val actViewModel: MainViewModel by activityViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentBetSucessoBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val action = BetSuccessFragmentDirections.actionBetSuccessToInit()
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        handleObservers()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
        actViewModel.checkUserLoggedIn()
    }

    private fun setupView() {
        binding.tvSummonerName.text = args.summonerName
        binding.tvTipoPartida.text = getString(R.string.label_sucesso_subtext, args.gameType)
        binding.tvPartidaRules.text = getString(R.string.label_rules_one, args.gameType)
        Glide.with(binding.root.context).load(args.summonerProfilePhoto).into(binding.ivSummonerProfile)
        viewModel.handleEvent(BetSuccessUiEvent.GetLastMatch(args.puuid))
    }

    private fun handleObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.lolBetSuccessState.collectLatest {
                        handleLolProfileUiState(it)
                    }
                }
                launch {
                    actViewModel.mainState.collectLatest {
                        handleMainUiState(it)
                    }
                }

            }
        }
    }


    private fun handleMainUiState(state: MainUiState) {
       /* when(state){
            is MainUiState.Initial -> {}
            is MainUiState.UserNotLoggedIn -> {
            }
            is MainUiState.UserLoggedIn -> {
                val user = state.user
                setupUserData(user)

        }*/
    }

    private fun handleLolProfileUiState(state: BetSuccessUiState) {
        when (state) {
            is BetSuccessUiState.Loading -> {

            }
            is BetSuccessUiState.Initial -> {
            }
            is BetSuccessUiState.GetLastMatchSuccess -> {

                setupLastMatchView(state.lastMatch)
            }
        }
    }

    private fun setupLastMatchView(lastMatch: MatchHistoryResponse) {
        if (lastMatch.live_match != null){
            setupLastLiveMatch(lastMatch.live_match)
        }else{
            setupLastMatchHistory(lastMatch.summoners_rift[0])
        }
    }

    private fun setupLastLiveMatch(matchHistory: LiveMatchHistory){
        binding.layoutLiveGame.root.visibility = View.VISIBLE
        binding.layoutGameMatchHistory.root.visibility = View.INVISIBLE
        binding.layoutLiveGame.tvData.text = matchHistory.gamestartTime
        Glide.with(binding.root.context).load(matchHistory.championPlayed).into(binding.layoutLiveGame.ivChampionPlayed)
        binding.layoutLiveGame.tvGameMode.text = matchHistory.gameType

        Glide.with(binding.root.context).load(matchHistory.participants[0].championImg).into(binding.layoutLiveGame.ivTeamAChampion1)
        Glide.with(binding.root.context).load(matchHistory.participants[1].championImg).into(binding.layoutLiveGame.ivTeamAChampion2)
        Glide.with(binding.root.context).load(matchHistory.participants[2].championImg).into(binding.layoutLiveGame.ivTeamAChampion3)
        Glide.with(binding.root.context).load(matchHistory.participants[3].championImg).into(binding.layoutLiveGame.ivTeamAChampion4)
        Glide.with(binding.root.context).load(matchHistory.participants[4].championImg).into(binding.layoutLiveGame.ivTeamAChampion5)

        Glide.with(binding.root.context).load(matchHistory.participants[5].championImg).into(binding.layoutLiveGame.ivTeamBChampion1)
        Glide.with(binding.root.context).load(matchHistory.participants[6].championImg).into(binding.layoutLiveGame.ivTeamBChampion2)
        Glide.with(binding.root.context).load(matchHistory.participants[7].championImg).into(binding.layoutLiveGame.ivTeamBChampion3)
        Glide.with(binding.root.context).load(matchHistory.participants[8].championImg).into(binding.layoutLiveGame.ivTeamBChampion4)
        Glide.with(binding.root.context).load(matchHistory.participants[9].championImg).into(binding.layoutLiveGame.ivTeamBChampion5)
    }

    private fun setupLastMatchHistory(matchHistory: MatchHistory){

        binding.layoutLiveGame.root.visibility = View.GONE
        binding.layoutGameMatchHistory.root.visibility = View.VISIBLE
        Glide.with(binding.root.context).load(matchHistory.championImg).into(binding.layoutGameMatchHistory.ivChampionPlayed)
        binding.layoutGameMatchHistory.tvStatusPartida.text = matchHistory.result
        binding.layoutGameMatchHistory.tvKda.text = matchHistory.kda
        binding.layoutGameMatchHistory.tvData.text = matchHistory.timeString
        binding.layoutGameMatchHistory.tvGameMode.text = matchHistory.gameType

        if (matchHistory.win){
            val colorBackground = ContextCompat.getColor(binding.root.context, R.color.the_green_light)
            val colorText = ContextCompat.getColor(binding.root.context, R.color.shamrock)
            binding.layoutGameMatchHistory.root.setBackgroundColor(colorBackground)
            binding.layoutGameMatchHistory.tvStatusPartida.setTextColor(colorText)
        }else{
            val color = ContextCompat.getColor(binding.root.context, R.color.pinkish)
            val colorText = ContextCompat.getColor(binding.root.context, R.color.red)
            binding.layoutGameMatchHistory.root.setBackgroundColor(color)
            binding.layoutGameMatchHistory.tvStatusPartida.setTextColor(colorText)
        }

        Glide.with(binding.root.context).load(matchHistory.teams[0].champImg).into(binding.layoutGameMatchHistory.ivTeamAChampion1)
        Glide.with(binding.root.context).load(matchHistory.teams[1].champImg).into(binding.layoutGameMatchHistory.ivTeamAChampion2)
        Glide.with(binding.root.context).load(matchHistory.teams[2].champImg).into(binding.layoutGameMatchHistory.ivTeamAChampion3)
        Glide.with(binding.root.context).load(matchHistory.teams[3].champImg).into(binding.layoutGameMatchHistory.ivTeamAChampion4)
        Glide.with(binding.root.context).load(matchHistory.teams[4].champImg).into(binding.layoutGameMatchHistory.ivTeamAChampion5)

        Glide.with(binding.root.context).load(matchHistory.teams[5].champImg).into(binding.layoutGameMatchHistory.ivTeamBChampion1)
        Glide.with(binding.root.context).load(matchHistory.teams[6].champImg).into(binding.layoutGameMatchHistory.ivTeamBChampion2)
        Glide.with(binding.root.context).load(matchHistory.teams[7].champImg).into(binding.layoutGameMatchHistory.ivTeamBChampion3)
        Glide.with(binding.root.context).load(matchHistory.teams[8].champImg).into(binding.layoutGameMatchHistory.ivTeamBChampion4)
        Glide.with(binding.root.context).load(matchHistory.teams[9].champImg).into(binding.layoutGameMatchHistory.ivTeamBChampion5)
    }
}