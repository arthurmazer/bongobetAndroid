package com.mazer.bongobet.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mazer.bongobet.databinding.ItemBetBinding
import com.mazer.bongobet.domain.entities.BetType
import com.mazer.bongobet.domain.entities.BetTypeListUi

class BetTypeAdapter():  RecyclerView.Adapter<BetTypeViewHolder>() {

    private var betList: ArrayList<BetTypeListUi> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetTypeViewHolder {
        val binding = ItemBetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BetTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BetTypeViewHolder, position: Int) {
        holder.bind(betList[position])
    }

    fun setList(list: List<BetTypeListUi>) {
        this.betList = ArrayList(list)
        notifyDataSetChanged()
    }

    fun getListBet(): List<BetTypeListUi> {
        return betList.filter { it.isChecked }
    }

    override fun getItemCount(): Int =  betList.size
}