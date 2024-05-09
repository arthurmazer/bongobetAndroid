package com.mazer.bongobet.ui.common

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import androidx.core.content.ContextCompat
import com.mazer.bongobet.R
import com.mazer.bongobet.domain.entities.BetTypeListUi
import com.mazer.bongobet.domain.entities.UserBet
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

inline fun <reified T : java.io.Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
    SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
}

inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
    SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
}

fun String.formatDate(): String {
    val formatoEntrada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val formatoSaida = SimpleDateFormat("dd/MM/yyyy - HH:mm:ss", Locale.getDefault())
    return formatoSaida.format(formatoEntrada.parse(this) ?: "")
}

fun String.getColor(context: Context): Int{
    return when (this){
        "white" -> {
            ContextCompat.getColor(context, R.color.white)
        }
        "gold" -> {
            ContextCompat.getColor(context, R.color.gold)
        }
        "black" -> {
            ContextCompat.getColor(context, R.color.black)
        }
        "blue" -> {
            ContextCompat.getColor(context, R.color.boston_blue)
        }
        "green" -> {
            ContextCompat.getColor(context, R.color.the_green_light)
        }
        "yellow" -> {
            ContextCompat.getColor(context, R.color.yellow)
        }
        "purple" -> {
            ContextCompat.getColor(context, R.color.purple_200)
        }
        "red" -> {
            ContextCompat.getColor(context, R.color.red)
        }
        else -> {
            ContextCompat.getColor(context, R.color.black)
        }
    }
}

fun List<BetTypeListUi>.toUserBetList(): ArrayList<UserBet> {
    val arrayUserBet: ArrayList<UserBet> = arrayListOf()
    for (item in this){
        arrayUserBet.add(item.userBet)
    }
    return arrayUserBet
}

fun List<UserBet>.toIdBetStringArray(): String {
    var returnStr = ""
    for (item in this){
        returnStr += item.betType.id.toString() + ";"
    }
    return returnStr.dropLast(1)
}

fun List<UserBet>.toQuantityBetStringArray(): String {
    var returnStr = ""
    for (item in this){
        returnStr += item.quantity.toString() + ";"
    }
    return returnStr.dropLast(1)
}
