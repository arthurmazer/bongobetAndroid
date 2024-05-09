package com.mazer.bongobet.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mazer.bongobet.databinding.ItemBetHistoryBinding
import com.mazer.bongobet.databinding.ItemBetHistoryTitleBinding
import com.mazer.bongobet.domain.entities.UserBet

class BetHistoryAdapter :   RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var betList: ArrayList<Any> = arrayListOf()

    companion object{
        const val BET_TITLE = 0
        const val BET_CONTENT = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            BET_TITLE -> {
                val binding = ItemBetHistoryTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BetHistoryTitleViewHolder(binding)
            }
            BET_CONTENT -> {
                val binding = ItemBetHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BetHistoryViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }

    }

    override fun onBindViewHolder(holder:  RecyclerView.ViewHolder, position: Int) {
        val item = betList[position]
        when (holder.itemViewType) {
            BET_TITLE -> {
                val title = item as String
                val viewHolder = holder as BetHistoryTitleViewHolder
                viewHolder.bind(title)
            }
            BET_CONTENT -> {
                val bet = item as UserBet
                val viewHolder = holder as BetHistoryViewHolder
                viewHolder.bind(bet)
            }
        }
    }

    fun addTitleToList(title: String) {
        this.betList.add(title)
        notifyItemInserted(this.betList.size-1)
    }

    fun addToList(userBet: UserBet) {
        this.betList.add(userBet)
        notifyItemInserted(this.betList.size-1)
    }
    override fun getItemCount(): Int =  betList.size

    override fun getItemViewType(position: Int): Int {
        return when (betList[position]) {
            is UserBet -> BET_CONTENT
            is String -> BET_TITLE
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }


}