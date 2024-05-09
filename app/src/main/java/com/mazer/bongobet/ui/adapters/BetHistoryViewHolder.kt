package com.mazer.bongobet.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mazer.bongobet.R
import com.mazer.bongobet.databinding.ItemBetConfirmationBinding
import com.mazer.bongobet.databinding.ItemBetHistoryBinding
import com.mazer.bongobet.domain.entities.UserBet
import com.mazer.bongobet.ui.common.StatusBet
import com.mazer.bongobet.ui.common.getColor

class BetHistoryViewHolder (private val binding: ItemBetHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(userBet: UserBet) {
        val context = binding.root.context
        binding.tvBetName.text = userBet.betType.name
        binding.tvBetDescription.text = userBet.betType.condicao
        binding.tvLabelOdds.text = context.getString(R.string.label_bet_odds,
            "%.2f".format(userBet.betType.odds)
        )
        binding.tvBetName.setTextColor(userBet.betType.color.getColor(binding.root.context))
        binding.tvLabelQuantity.text = context.getString(R.string.label_bet_quantity, "%.2f".format(userBet.quantity))
        binding.tvLabelProfit.visibility = View.GONE
        binding.tvBetNumber.text = "#${userBet.betType.id}"
        binding.tvBetNumber.setTextColor(userBet.betType.color.getColor(binding.root.context))


        when(userBet.statusBet){
            StatusBet.APOSTADO -> {
                binding.tvLabelStatus.text = context.getString(R.string.label_bet_status, "Aguardando Jogo")
            }
            StatusBet.DERROTA -> {
                binding.tvLabelStatus.text = context.getString(R.string.label_bet_status, "Derrota")
            }
            StatusBet.EXPIRADA -> {
                binding.tvLabelStatus.text = context.getString(R.string.label_bet_status, "Expirada")
            }
            StatusBet.VITORIA -> {
                binding.tvLabelStatus.text = context.getString(R.string.label_bet_status, "Vitória")
                binding.tvLabelProfit.visibility = View.VISIBLE
                binding.tvLabelProfit.text = context.getString(R.string.label_bet_profit, "%.2f".format(userBet.betType.odds*userBet.quantity) )
            }
            StatusBet.PEDIDO_REEMBOLSO -> {
                binding.tvLabelStatus.text = context.getString(R.string.label_bet_status, "Pedido de Reembolso")

            }
            StatusBet.REEMBOLSADO -> {
                binding.tvLabelStatus.text = context.getString(R.string.label_bet_status, "Reembolsado")
            }
        }
    }
}