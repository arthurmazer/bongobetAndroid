package com.mazer.bongobet.ui.lolprofile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mazer.bongobet.domain.entities.MatchHistory

class MatchesPageAdapter(activity: FragmentActivity, private val matchHistory: List<MatchHistory>) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                //SR
                MatchHistorySRFragment.newInstance(matchHistory)
            }
            1 -> {
                MatchHistoryTFTFragment.newInstance(matchHistory)
            }
            else -> {
                MatchHistorySRFragment.newInstance(matchHistory)
            }
        }

    }
}