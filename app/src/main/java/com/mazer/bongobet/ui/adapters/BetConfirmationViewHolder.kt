package com.mazer.bongobet.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mazer.bongobet.R
import com.mazer.bongobet.databinding.ItemBetConfirmationBinding
import com.mazer.bongobet.domain.entities.UserBet
import com.mazer.bongobet.ui.common.getColor

class BetConfirmationViewHolder (private val binding: ItemBetConfirmationBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(userBet: UserBet) {
        val context = binding.root.context
        binding.tvBetName.text = userBet.betType.name
        binding.tvBetDescription.text = userBet.betType.condicao
        binding.tvLabelOdds.text = context.getString(R.string.label_bet_odds, userBet.betType.odds.toString())
        binding.tvBetName.setTextColor(userBet.betType.color.getColor(binding.root.context))
        binding.tvLabelQuantity.text = context.getString(R.string.label_bet_quantity, userBet.quantity.toString())
    }

}