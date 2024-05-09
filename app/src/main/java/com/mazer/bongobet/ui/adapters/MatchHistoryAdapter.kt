package com.mazer.bongobet.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mazer.bongobet.databinding.ItemMatchHistoryBinding
import com.mazer.bongobet.domain.entities.MatchHistory

class MatchHistoryAdapter()  :  RecyclerView.Adapter<MatchHistoryViewHolder>() {

    private var matchHistoryList: ArrayList<MatchHistory> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchHistoryViewHolder {
        val binding = ItemMatchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchHistoryViewHolder, position: Int) {
        holder.bind(matchHistoryList[position])
    }

    fun setList(list: List<MatchHistory>) {
        this.matchHistoryList = ArrayList(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =  matchHistoryList.size
}