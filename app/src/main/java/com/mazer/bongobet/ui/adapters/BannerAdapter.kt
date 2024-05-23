package com.mazer.bongobet.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mazer.bongobet.databinding.ItemBetBinding
import com.mazer.bongobet.databinding.ItemCarouselBannerBinding
import com.mazer.bongobet.domain.entities.Banner

class BannerAdapter : RecyclerView.Adapter<BannerViewHolder>() {

    var bannerList: List<Banner> = listOf()

    companion object {
        private const val EMPTY_LIST = 0
        private const val SINGLE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = ItemCarouselBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val positionInList: Int = position % bannerList.size
        holder.bind(bannerList[positionInList])
    }

    fun setList(list: List<Banner>) {
        this.bannerList = when(list.size) {
            EMPTY_LIST -> emptyList()
            SINGLE_ITEM -> list
            else -> { listOf(list.last()) + list + listOf(list.first()) }
        }
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int =  bannerList.size
}
