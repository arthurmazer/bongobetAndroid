package com.mazer.bongobet.ui.adapters

import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mazer.bongobet.R
import com.mazer.bongobet.databinding.ItemBetBinding
import com.mazer.bongobet.domain.entities.BetTypeListUi

class BetTypeViewHolder (private val binding: ItemBetBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(betTypeUiList: BetTypeListUi) {
        Glide.with(binding.root.context).load(betTypeUiList.userBet.betType.urlThumb).into(binding.ivBetType)
        binding.tvBetName.text = betTypeUiList.userBet.betType.name
        binding.tvBetDescription.text = betTypeUiList.userBet.betType.condicao
        binding.tvOdds.text = betTypeUiList.userBet.betType.odds.toString()
        binding.tvBetName.setTextColor(getColor(betTypeUiList.userBet.betType.color))

        binding.checkboxAposta.isChecked = betTypeUiList.isChecked

        binding.checkboxAposta.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.tvQuantityBet.isEnabled = isChecked
            betTypeUiList.isChecked = isChecked
        }

        binding.tvQuantityBet.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                betTypeUiList.userBet.quantity = binding.tvQuantityBet.text.toString().toFloat()

            }

        })
    }

    fun getColor(color: String): Int{
        return when (color){
            "white" -> {
                ContextCompat.getColor(binding.root.context,R.color.white)
            }
            "gold" -> {
                ContextCompat.getColor(binding.root.context,R.color.gold)
            }
            "black" -> {
                ContextCompat.getColor(binding.root.context,R.color.black)
            }
            "blue" -> {
                ContextCompat.getColor(binding.root.context,R.color.boston_blue)
            }
            "green" -> {
                ContextCompat.getColor(binding.root.context,R.color.the_green_light)
            }
            "yellow" -> {
                ContextCompat.getColor(binding.root.context,R.color.yellow)
            }
            "purple" -> {
                ContextCompat.getColor(binding.root.context,R.color.purple_200)
            }
            "red" -> {
                ContextCompat.getColor(binding.root.context,R.color.red)
            }
            else -> {
                ContextCompat.getColor(binding.root.context,R.color.black)
            }
        }
    }
}