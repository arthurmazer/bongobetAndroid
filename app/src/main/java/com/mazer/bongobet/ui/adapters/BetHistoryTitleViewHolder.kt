package com.mazer.bongobet.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mazer.bongobet.R
import com.mazer.bongobet.databinding.ItemBetConfirmationBinding
import com.mazer.bongobet.databinding.ItemBetHistoryBinding
import com.mazer.bongobet.databinding.ItemBetHistoryTitleBinding
import com.mazer.bongobet.domain.entities.UserBet
import com.mazer.bongobet.ui.common.getColor

class BetHistoryTitleViewHolder (private val binding: ItemBetHistoryTitleBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(title: String) {
        binding.tvBetTitle.text = title
    }

}