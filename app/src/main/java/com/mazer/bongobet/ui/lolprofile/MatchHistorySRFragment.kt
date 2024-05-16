package com.mazer.bongobet.ui.lolprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mazer.bongobet.databinding.FragmentMatchHistoryBinding
import com.mazer.bongobet.domain.entities.MatchHistory
import com.mazer.bongobet.ui.adapters.MatchHistoryAdapter
import com.mazer.bongobet.ui.common.parcelableArrayList

class MatchHistorySRFragment: Fragment() {

    private lateinit var adapter: MatchHistoryAdapter
    private lateinit var binding: FragmentMatchHistoryBinding
    private var matchHistory: List<MatchHistory>? = null

    companion object {
        fun newInstance(matchHistory: List<MatchHistory>): MatchHistorySRFragment {
            val fragment = MatchHistorySRFragment()
            val arrayMatchHistory = ArrayList(matchHistory)
            fragment.arguments = Bundle().apply {
                putParcelableArrayList("matchHistory", arrayMatchHistory)
            }
            return fragment

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentMatchHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matchHistory = arguments?.parcelableArrayList("matchHistory")

        setupView(ArrayList(matchHistory))
    }

    private fun setupView(matchHistory: ArrayList<MatchHistory>?) {
        setupAdapter()

        adapter.setList(matchHistory?.toList() ?: return)
    }


    private fun setupAdapter() {
        adapter = MatchHistoryAdapter()
        binding.rvMatchHistory.adapter = adapter
    }

}