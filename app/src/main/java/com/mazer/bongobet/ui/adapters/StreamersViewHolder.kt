package com.mazer.bongobet.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mazer.bongobet.databinding.ItemBetTwitchBinding
import com.mazer.bongobet.domain.entities.Streamer
import com.mazer.bongobet.ui.common.getRankDrawable

class StreamersViewHolder(
    private val binding: ItemBetTwitchBinding,
    private val onTwitchWatchClicked: (channelId: String) -> Unit,
    private val onBetClicked: (summonerAndTag: String, gameTag: String, puuid: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(streamer: Streamer) {
        val context = binding.root.context
        Glide.with(context).load(streamer.thumbUrl).into(binding.ivPlayerIcon)
        Glide.with(context).load(streamer.tier.getRankDrawable()).into(binding.ivStreamerRank)
        binding.tvStreamerName.text = streamer.name
        binding.tvTwitchChannel.text = streamer.channelId


        if (streamer.isOnline){
            binding.ivIconOnline.visibility = View.VISIBLE
            binding.ivIconOffline.visibility = View.GONE
            binding.tvWatchTwitchChannel.visibility = View.VISIBLE
        }else{
            binding.ivIconOnline.visibility = View.GONE
            binding.ivIconOffline.visibility = View.VISIBLE
            binding.tvWatchTwitchChannel.visibility = View.GONE
        }

        if (streamer.isPlaying){
            binding.tvStatus.visibility = View.VISIBLE
        }else{
            binding.tvStatus.visibility = View.GONE
        }

        binding.tvWatchTwitchChannel.setOnClickListener {
            onTwitchWatchClicked.invoke(streamer.channelId)
        }

        binding.btnApostar.setOnClickListener {
            onBetClicked.invoke(streamer.gameNickName, streamer.gameTag, streamer.puuid)
        }

    }

}