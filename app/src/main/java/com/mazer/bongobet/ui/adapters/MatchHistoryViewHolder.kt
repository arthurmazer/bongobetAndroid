package com.mazer.bongobet.ui.adapters

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mazer.bongobet.R
import com.mazer.bongobet.databinding.ItemMatchHistoryBinding
import com.mazer.bongobet.domain.entities.MatchHistory

class MatchHistoryViewHolder(private val binding: ItemMatchHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(matchHistory: MatchHistory) {
        Glide.with(binding.root.context).load(matchHistory.championImg).into(binding.ivChampionPlayed)
        binding.tvStatusPartida.text = matchHistory.result
        binding.tvKda.text = matchHistory.kda
        binding.tvData.text = matchHistory.timeString
        binding.tvGameMode.text = matchHistory.gameType

        if (matchHistory.win){
            val colorBackground = ContextCompat.getColor(binding.root.context, R.color.the_green_light)
            val colorText = ContextCompat.getColor(binding.root.context, R.color.shamrock)
            binding.root.setBackgroundColor(colorBackground)
            binding.tvStatusPartida.setTextColor(colorText)
        }else{
            val color = ContextCompat.getColor(binding.root.context, R.color.pinkish)
            val colorText = ContextCompat.getColor(binding.root.context, R.color.red)
            binding.root.setBackgroundColor(color)
            binding.tvStatusPartida.setTextColor(colorText)
        }

        if (matchHistory.result.lowercase() == "remake"){
            val color = ContextCompat.getColor(binding.root.context, R.color.light_grey)
            val colorText = ContextCompat.getColor(binding.root.context, R.color.grey)
            binding.root.setBackgroundColor(color)
            binding.tvStatusPartida.setTextColor(colorText)
        }

        Glide.with(binding.root.context).load(matchHistory.teams[0].champImg).into(binding.ivTeamAChampion1)
        Glide.with(binding.root.context).load(matchHistory.teams[1].champImg).into(binding.ivTeamAChampion2)
        Glide.with(binding.root.context).load(matchHistory.teams[2].champImg).into(binding.ivTeamAChampion3)
        Glide.with(binding.root.context).load(matchHistory.teams[3].champImg).into(binding.ivTeamAChampion4)
        Glide.with(binding.root.context).load(matchHistory.teams[4].champImg).into(binding.ivTeamAChampion5)

        Glide.with(binding.root.context).load(matchHistory.teams[5].champImg).into(binding.ivTeamBChampion1)
        Glide.with(binding.root.context).load(matchHistory.teams[6].champImg).into(binding.ivTeamBChampion2)
        Glide.with(binding.root.context).load(matchHistory.teams[7].champImg).into(binding.ivTeamBChampion3)
        Glide.with(binding.root.context).load(matchHistory.teams[8].champImg).into(binding.ivTeamBChampion4)
        Glide.with(binding.root.context).load(matchHistory.teams[9].champImg).into(binding.ivTeamBChampion5)
    }
}