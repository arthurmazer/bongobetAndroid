package com.mazer.bongobet.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mazer.bongobet.databinding.ItemBetTwitchBinding
import com.mazer.bongobet.domain.entities.Streamer

class StreamersAdapter(private val onTwitchWatchClicked: (channelId: String) -> Unit, private val onBetClicked: (summonerName: String, gameTag: String, puuid: String) -> Unit) :  RecyclerView.Adapter<StreamersViewHolder>() {

    private var streamersList: ArrayList<Streamer> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamersViewHolder {
        val binding = ItemBetTwitchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StreamersViewHolder(binding, onTwitchWatchClicked, onBetClicked)
    }

    override fun onBindViewHolder(holder: StreamersViewHolder, position: Int) {
        holder.bind(streamersList[position])
    }

    fun setList(list: List<Streamer>) {
        this.streamersList = ArrayList(list.sortedByDescending { it.isOnline })
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int =  streamersList.size
}