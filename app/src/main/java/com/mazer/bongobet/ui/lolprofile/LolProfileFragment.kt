package com.mazer.bongobet.ui.lolprofile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.mazer.bongobet.R
import com.mazer.bongobet.databinding.FragmentLolProfileBinding
import com.mazer.bongobet.domain.entities.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LolProfileFragment : Fragment() {

    private lateinit var binding: FragmentLolProfileBinding
    private var summonerName: String = ""
    private var puuid: String = ""
    private var summonerProfilePhoto = ""
    private val viewModel : LolProfileViewModel by viewModel()
    private val args: LolProfileFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLolProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgs()
        handleObservers()
        setupView()
    }




    private fun getArgs() {
        summonerName = args.summonerName
        puuid = args.puuid
    }

    private fun setupView(){
        setupMatchHistory()

        binding.btnBet.setOnClickListener {
            goToBetScreen(puuid, summonerName)
        }
    }

    private fun setupMatchHistory() {
        viewModel.handleEvent(LolProfileUiEvent.GetLolProfile(puuid, summonerName))
    }

    private fun setupAdapter(matchHistory: List<MatchHistory>) {
        val adapter = MatchesPageAdapter(requireActivity(), matchHistory)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position){
                0 -> tab.text = getString(R.string.pager_opt_solo_duo)
                1 -> tab.text = getString(R.string.pager_opt_tft)
            }
        }.attach()
    }

    private fun handleObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.lolProfileState.collectLatest {
                        handleLolProfileUiState(it)
                    }
                }
            }
        }
    }

    private fun finishEvents(){
        viewModel.finishedEvent()
    }


    private fun handleLolProfileUiState(state: LolProfileUiState) {
        when(state){
            is LolProfileUiState.Loading -> {
                showLoading(true)
                toggleBetButton(true)
            }
            is LolProfileUiState.MatchHistorySucess -> {
                showLoading(false)
                toggleBetButton(false)
                setupAdapter(state.listMatch.summoners_rift)
                //setupLiveMatch(state.listMatch.live_match)
                finishEvents()
            }
            is LolProfileUiState.MatchHistoryError -> {
                showLoading(false)
                //TODO SHOW ERROR
                finishEvents()
            }
            is LolProfileUiState.SummonerDataLoadedSuccess -> {
                binding.loadingSummonerData.visibility = View.GONE
                setupLolProfileUi(state.lolUser)
                finishEvents()
            }
            is LolProfileUiState.SummonerDataLoadedError -> {
                finishEvents()
            }
        }

    }

    private fun toggleBetButton(isLoading: Boolean) {
        if (isLoading){
            binding.btnBet.text = getText(R.string.btn_aguarde)
            binding.btnBet.isEnabled = true
        }else{
            binding.btnBet.text = getText(R.string.btn_apostar)
            binding.btnBet.isEnabled = true
        }
    }

    private fun showLoading(loading: Boolean){
        if (loading){
            binding.shimmerViewLayout.visibility = View.VISIBLE
            binding.shimmerViewLayout.startShimmer()
        }else{
            binding.shimmerViewLayout.stopShimmer()
            binding.shimmerViewLayout.visibility = View.GONE
        }
    }

    private fun setupLolProfileUi(lolUser: LolUser){
        summonerProfilePhoto = lolUser.urlProfile
        Glide.with(binding.root.context).load(lolUser.urlProfile).into(binding.ivSummonerProfile)
        binding.tvSummonerLevel.text = getString(R.string.level_placeholder, lolUser.summonerLevel.toString())
        setupRanks(lolUser.ranks)

    }

    private fun setupRanks(ranks: List<Rank>) {
        ranks.forEach { rank ->
            when (rank.tier.lowercase()){
                "iron" -> {
                    setRankData(rank, R.drawable.iron)
                }
                "bronze" -> {
                    setRankData(rank, R.drawable.bronze)
                }
                "silver" -> {
                    setRankData(rank, R.drawable.silver)
                }
                "gold" -> {
                    setRankData(rank, R.drawable.gold)
                }
                "platinum" -> {
                    setRankData(rank, R.drawable.platinum)
                }
                "emerald" -> {
                    setRankData(rank, R.drawable.emerald)
                }
                "diamond" -> {
                    setRankData(rank, R.drawable.diamond)
                }
                "master" -> {
                    setRankData(rank, R.drawable.master)
                }
                "grandmaster" -> {
                    setRankData(rank, R.drawable.grandmaster)
                }
                "challenger" -> {
                    setRankData(rank, R.drawable.challenger)
                }
                else -> {
                    setRankData(rank, R.drawable.unranked, true)
                }

            }
        }
    }



    @SuppressLint("SetTextI18n")
    private fun setRankData(rank: Rank, imgResource: Int, isUnranked: Boolean = false){
        when(rank.queueType){
            "Ranked Solo/Duo" -> {
                Glide.with(binding.root.context).load(imgResource).into(binding.ivRankSoloDuo)
                binding.tvRankSoloDuoName.text = "${rank.tier} ${rank.rank}"
                if(!isUnranked) {
                    val winrate = (100 * rank.wins / (rank.wins + rank.losses))
                    binding.tvRankSoloDuoWinrate.text = "$winrate%"
                    binding.tvRankSoloDuoVitorias.text = rank.wins.toString()
                    binding.tvRankSoloDuoDerrotas.text = rank.losses.toString()
                }else{
                    binding.layoutRankedSoloDuoStats.visibility = View.GONE
                }
            }
            "Ranked Flex" -> {
                Glide.with(binding.root.context).load(imgResource).into(binding.ivRankFlex)
                binding.tvRankFlexName.text = "${rank.tier} ${rank.rank}"
                if(!isUnranked) {
                    val winrate = (100*rank.wins/(rank.wins+rank.losses))
                    binding.tvRankFlexWinrate.text = "$winrate%"
                    binding.tvRankFlexVitorias.text = rank.wins.toString()
                    binding.tvRankFlexDerrotas.text = rank.losses.toString()
                }else{
                    binding.layoutRankedFlexStats.visibility = View.GONE
                }
            }
            "Ranked TFT" -> {
                Glide.with(binding.root.context).load(imgResource).into(binding.ivRankTft)
                binding.tvRankTftName.text = "${rank.tier} ${rank.rank}"
                if(!isUnranked) {
                    val winrate = (100 * rank.wins / (rank.wins + rank.losses))
                    binding.tvRankTftWinrate.text = "${winrate.toString()}%"
                    binding.tvRankTftVitorias.text = rank.wins.toString()
                    binding.tvRankTftDerrotas.text = rank.losses.toString()
                }else{
                    binding.layoutRankedTftStats.visibility = View.GONE
                }


            }
        }
    }

    private fun goToBetScreen(puuid: String, summonerName: String){
        val action = LolProfileFragmentDirections.actionLolProfileToBetType(
            puuid, summonerName, summonerProfilePhoto
        )
        findNavController().navigate(action)
    }



}