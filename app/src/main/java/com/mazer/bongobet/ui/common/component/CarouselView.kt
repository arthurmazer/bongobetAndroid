package com.mazer.bongobet.ui.common.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.mazer.bongobet.R
import com.mazer.bongobet.domain.entities.Banner
import com.mazer.bongobet.ui.adapters.BannerAdapter

class CarouselView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private var mAdapter: BannerAdapter
    var rvBanner: RecyclerView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_layout_carousel_view, this, true)

        rvBanner = view.findViewById(R.id.rvBanner)

        mAdapter = BannerAdapter()
        rvBanner.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvBanner.adapter = mAdapter
        setRecyclerViewListeners()

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rvBanner)
    }

    private fun setRecyclerViewListeners() {
        rvBanner.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    checkAndRedirectEndlessCarousel()
                }
            }
        })
    }


    fun addList(list: List<Banner>) {
        mAdapter.setList(list)
        rvBanner.layoutManager?.scrollToPosition(1)
    }

    private fun checkAndRedirectEndlessCarousel() {
        val position = getCarouselCurrentPosition()%mAdapter.bannerList.size
        if (position == 0) {
            rvBanner.layoutManager?.scrollToPosition(mAdapter.bannerList.size-2)
        } else if (position == mAdapter.bannerList.size-1){
            rvBanner.layoutManager?.scrollToPosition(1)
        }
    }

    private fun getCarouselCurrentPosition() : Int {
        return (rvBanner.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
    }
}