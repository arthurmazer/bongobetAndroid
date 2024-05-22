package com.mazer.bongobet.ui.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mazer.bongobet.common.utils.addCurrencyMaskBR
import com.mazer.bongobet.databinding.ItemBetBinding
import com.mazer.bongobet.domain.entities.BetTypeListUi
import com.mazer.bongobet.domain.entities.UserBet
import com.mazer.bongobet.ui.common.getColor

class BetTypeViewHolder (private val binding: ItemBetBinding, private val onBetChecked: (isChecked: Boolean, userBet: UserBet) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    fun bind(betTypeUiList: BetTypeListUi) {
        Glide.with(binding.root.context).load(betTypeUiList.userBet.betType.urlThumb).into(binding.ivBetType)
        binding.tvBetName.text = betTypeUiList.userBet.betType.name
        binding.tvBetDescription.text = betTypeUiList.userBet.betType.condicao
        binding.tvOdds.text = betTypeUiList.userBet.betType.odds.toString()
        binding.tvBetName.setTextColor(betTypeUiList.userBet.betType.color.getColor(binding.root.context))

        binding.cbApostar.isChecked = betTypeUiList.isChecked
        binding.etBetValue.addCurrencyMaskBR()
        binding.etBetValue.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val betQuantity = s.toString().replace("R$", "").replace(",", ".")
                val a = ""
                betTypeUiList.userBet.quantity = betQuantity.trim().toFloat()
            }

        })

        binding.cbApostar.setOnCheckedChangeListener { buttonView, isChecked ->
            betTypeUiList.isChecked = isChecked
            onBetChecked.invoke(isChecked, betTypeUiList.userBet)
            if (isChecked)
                binding.etBetValue.visibility = View.VISIBLE
            else
                binding.etBetValue.visibility = View.GONE
        }
    }

}