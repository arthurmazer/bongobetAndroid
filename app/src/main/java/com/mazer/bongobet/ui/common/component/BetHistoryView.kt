package com.mazer.bongobet.ui.common.component

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.mazer.bongobet.R
import com.mazer.bongobet.databinding.CustomLayoutBetHistoryBinding
import com.mazer.bongobet.ui.adapters.BetHistoryAdapter

class BetHistoryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var typedArray: TypedArray
    private lateinit var binding: CustomLayoutBetHistoryBinding

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet? = null) {
        try {
            binding = CustomLayoutBetHistoryBinding.inflate(LayoutInflater.from(context), this, true)
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.BetHistoryView)

            val title = typedArray.getString(R.styleable.BetHistoryView_title)
            //binding.tvBetTitle.text = title
        } catch (e: Exception) {
            throw Exception()
        }
        requestLayout()
    }

    fun setAdapter(adapter: BetHistoryAdapter) {
        binding.rvBets.adapter = adapter
    }

}