package com.mazer.bongobet.ui.lolprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mazer.bongobet.databinding.FragmentMatchHistoryBinding

class MatchHistoryTFTFragment: Fragment() {

    private lateinit var binding: FragmentMatchHistoryBinding
    private lateinit var matchHistory: List<Any>

    companion object {
        fun newInstance(matchHistory: List<Any>): MatchHistoryTFTFragment {
            val fragment = MatchHistoryTFTFragment()
            fragment.arguments = Bundle().apply {
                putSerializable("matchHistory", ArrayList(matchHistory))
            }
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        //matchHistory = requireArguments().getSerializable("matchHistory") as List<Any> ?: emptyList()

        binding = FragmentMatchHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {

    }
}