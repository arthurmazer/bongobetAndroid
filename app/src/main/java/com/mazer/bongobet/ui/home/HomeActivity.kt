package com.mazer.bongobet.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mazer.bongobet.R
import com.mazer.bongobet.databinding.ActivityHomeBinding
import com.mazer.bongobet.domain.entities.Banner
import jp.wasabeef.glide.transformations.BlurTransformation
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel : HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bannerList = arrayListOf<Banner>()
        bannerList.add(
            Banner(
                R.drawable.league_of_legends_logo,
                getString(R.string.game_option_name_league_of_legends)
            )
        )
        bannerList.add(
            Banner(
                R.drawable.counter_strike_logo,
                getString(R.string.game_option_name_counter_strike2)
            )
        )
        bannerList.add(
            Banner(
                R.drawable.tft_logo,
                getString(R.string.game_option_name_tft)
            )
        )
        bannerList.add(
            Banner(
                R.drawable.valorant_logo,
                getString(R.string.game_option_name_valorant)
            )
        )
        binding.carouselView.addList(bannerList)
/*        Glide.with(this).load(R.drawable.league_of_legends_logo)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
            .into(binding.ivBackgroundImage)*/

    }
}