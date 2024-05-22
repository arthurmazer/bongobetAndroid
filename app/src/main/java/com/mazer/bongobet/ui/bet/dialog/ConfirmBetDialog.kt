package com.mazer.bongobet.ui.bet.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.mazer.bongobet.R
import com.mazer.bongobet.domain.entities.UserBet
import com.mazer.bongobet.ui.adapters.BetConfirmationAdapter
import com.mazer.bongobet.ui.common.parcelableArrayList

class ConfirmBetDialog(private val onConfirmSelected: () -> Unit) : DialogFragment() {


    private lateinit var adapter: BetConfirmationAdapter
    private var betList: List<UserBet>? = null

    companion object {
        fun newInstance(list: ArrayList<UserBet>, gameName: String, onConfirmSelected: () -> Unit): ConfirmBetDialog {
            val fragment = ConfirmBetDialog(onConfirmSelected)
            val args = Bundle()
            args.putParcelableArrayList("betlist", list)
            args.putString("gameName", gameName)
            fragment.arguments = args
            return fragment
        }
    }

    interface ConfirmDialogEventListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        betList = arguments?.parcelableArrayList("betlist")
        val gameName = arguments?.getString("gameName")

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_layout_confirmar_aposta, null)

            builder.setView(dialogView)

            val confirmButton = dialogView.findViewById<Button>(R.id.btn_confirmar_bet)
            val cancelButton = dialogView.findViewById<Button>(R.id.btn_cancelar_bet)
            val rvBetConfirmation = dialogView.findViewById<RecyclerView>(R.id.rv_bets_confirmation)
            val gameType = dialogView.findViewById<TextView>(R.id.tv_game_name)
            val gameMode = dialogView.findViewById<TextView>(R.id.tv_game_mode)

            adapter = BetConfirmationAdapter()
            rvBetConfirmation.adapter = adapter

            adapter.setList(betList ?: arrayListOf())
            if (betList?.isNotEmpty() == true){
                gameType.text = requireContext().getString(R.string.label_game_name, betList?.get(0)?.betType?.gametype)
            }

            gameMode.text = requireContext().getString(R.string.label_game_type, gameName)

            confirmButton.setOnClickListener {
                val listener = activity as? ConfirmDialogEventListener
                listener?.onDialogPositiveClick()
                onConfirmSelected.invoke()
                dismiss()
            }

            cancelButton.setOnClickListener {
                val listener = activity as? ConfirmDialogEventListener
                listener?.onDialogNegativeClick()
                dismiss()
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}