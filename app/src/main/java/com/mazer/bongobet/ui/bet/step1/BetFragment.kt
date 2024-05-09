package com.mazer.bongobet.ui.bet.step1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.mazer.bongobet.databinding.FragmentBetBinding
import com.mazer.bongobet.domain.entities.BetTypeListUi
import com.mazer.bongobet.domain.entities.GameType
import com.mazer.bongobet.domain.entities.UserBet
import com.mazer.bongobet.ui.adapters.BetTypeAdapter
import com.mazer.bongobet.ui.bet.dialog.ConfirmBetDialogFragment
import com.mazer.bongobet.ui.common.toUserBetList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BetFragment : Fragment() {

    private lateinit var binding: FragmentBetBinding
    private val viewModel: BetViewModel by viewModel()
    private var summonerName: String = ""
    private var puuid: String = ""
    private var summonerPhoto: String = ""
    private var idGameType : Int = 0
    private var gameTypeName: String = ""
    private val args: BetFragmentArgs by navArgs()
    private lateinit var adapterbetPicker: BetTypeAdapter
    private var betList: ArrayList<UserBet> = arrayListOf()
    private var userEmail: String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentBetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        summonerName = args.summonerName
        puuid = args.puuid
        summonerPhoto = args.summonerProfilePhoto

        setupView()
        setupAdapter()
        handleObservers()
    }

    private fun setupView() {
        binding.tvSummonerName.text = summonerName
        Glide.with(binding.root.context).load(summonerPhoto).into(binding.ivSummonerProfile)


        binding.btnAvancar.setOnClickListener {
            if (binding.radioGroup.isVisible){
                viewModel.handleEvents(BetUiEvent.LoadBetTypes(idGameType, puuid))
                manageStepVisibility(2)
            }else{
                betList = adapterbetPicker.getListBet().toUserBetList()
                val newFragment = ConfirmBetDialogFragment.newInstance(betList) {
                    viewModel.handleEvents(
                        BetUiEvent.ConfirmBet(
                            betList,
                            puuid
                        )
                    )
                }
                newFragment.show(parentFragmentManager, "confirm_bet")
            }
        }
    }


    private fun manageBackPressed(){
        if (binding.rvBets.isVisible){
            manageStepVisibility(1)
        }else{
            findNavController().popBackStack()
        }
    }


    private fun nextScreen(){
        val action = BetFragmentDirections.actionBetToBetSuccess(
            puuid,
            summonerName,
            summonerPhoto,
            gameTypeName
        )
        findNavController().navigate(action)
    }

    private fun handleObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.betTypeState.collectLatest {
                        handleBetTypeUiState(it)
                    }
                }
            }
        }
    }

    private fun handleBetTypeUiState(state: BetUiState) {
        when (state) {
            is BetUiState.Step -> {
                manageStepVisibility(state.step)
            }
            is BetUiState.Loading -> {}
            is BetUiState.GameTypeLoadedSuccess -> {
                setupGameTypeList(state.gameTypeList)
            }
            is BetUiState.GameTypeLoadedError -> {
                Snackbar.make(this.view ?: return, state.msg, Snackbar.LENGTH_LONG).show()
            }
            is BetUiState.BetTypesLoading -> {
                binding.loadingBets.visibility = View.VISIBLE
                binding.rvBets.visibility = View.GONE

            }
            is BetUiState.GameTypeSelected ->{
                binding.btnAvancar.isEnabled = true
            }
            is BetUiState.BetTypesLoadedSuccess -> {
                binding.loadingBets.visibility = View.GONE
                binding.rvBets.visibility = View.VISIBLE
                val betTypeUiList = arrayListOf<BetTypeListUi>()
                state.betList.forEach {
                    betTypeUiList.add(BetTypeListUi(UserBet(0,it, 0.0f), false))
                }
                adapterbetPicker.setList(betTypeUiList)
            }
            is BetUiState.BetCreatedSuccess -> {
                nextScreen()

            }
        }
    }

    private fun manageStepVisibility(step: Int) {
        when (step){
            2 -> {
                setupStep2()
            }
            else -> {
                setupStep1()
            }
        }
    }

    private fun setupStep2(){
        //load step 2 items
        binding.radioGroup.visibility = View.GONE
        binding.tvBetPickerLabel.visibility = View.VISIBLE
        binding.tvTipoPartida.text = gameTypeName
        binding.tvTipoPartida.visibility = View.VISIBLE
    }

    private fun setupStep1(){
        binding.radioGroup.visibility = View.VISIBLE
        binding.tvBetPickerLabel.visibility = View.GONE
        binding.rvBets.visibility = View.GONE
        binding.tvTipoPartida.visibility = View.GONE
    }

    private fun setupGameTypeList(gameTypeList: List<GameType>) {
        gameTypeList.forEach { gameType ->
            val radioButton = RadioButton(requireContext())
            radioButton.text = gameType.name
            binding.radioGroup.addView(radioButton)

            radioButton.setOnClickListener {
                idGameType = gameType.id
                gameTypeName = gameType.name
                viewModel.handleEvents(BetUiEvent.ChangeGame(gameType))
            }
        }
    }

    private fun setupAdapter() {
        adapterbetPicker = BetTypeAdapter()
        binding.rvBets.adapter = adapterbetPicker
    }



}