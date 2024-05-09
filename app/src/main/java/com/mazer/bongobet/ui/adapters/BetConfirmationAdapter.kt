package com.mazer.bongobet.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mazer.bongobet.databinding.ItemBetConfirmationBinding
import com.mazer.bongobet.domain.entities.BetTypeListUi
import com.mazer.bongobet.domain.entities.UserBet

class BetConfirmationAdapter :  RecyclerView.Adapter<BetConfirmationViewHolder>() {

    private var betList: ArrayList<UserBet> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetConfirmationViewHolder {
        val binding = ItemBetConfirmationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BetConfirmationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BetConfirmationViewHolder, position: Int) {
        holder.bind(betList[position])
    }

    fun setList(list: List<UserBet>) {
        this.betList = ArrayList(list)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int =  betList.size
}