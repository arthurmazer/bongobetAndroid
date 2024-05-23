package com.mazer.bongobet.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mazer.bongobet.databinding.ItemCarouselBannerBinding
import com.mazer.bongobet.domain.entities.Banner

class BannerViewHolder (private val binding: ItemCarouselBannerBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(banner: Banner) {
        Glide.with(binding.root.context).load(banner.imageId).into(binding.ivImgCarousel)
        binding.tvGameTitle.text = banner.gameName
    }
}


