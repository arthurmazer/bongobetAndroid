package com.mazer.bongobet.common.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.*

fun EditText.addCurrencyMaskBR() {
    val locale = Locale("pt", "BR")
    val numberFormat = NumberFormat.getCurrencyInstance(locale)

    val textWatcher = object : TextWatcher {
        var current = ""

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (s.toString() != current) {
                this@addCurrencyMaskBR.removeTextChangedListener(this)

                var cleanString = s.toString().replace(Regex("[^\\d]"), "")
                var parsed = cleanString.toDoubleOrNull() ?: 0.0
                var formatted = numberFormat.format(parsed / 100)

                current = formatted
                this@addCurrencyMaskBR.setText(formatted)
                this@addCurrencyMaskBR.setSelection(formatted.length)

                this@addCurrencyMaskBR.addTextChangedListener(this)
            }
        }
    }
    this.addTextChangedListener(textWatcher)
}
