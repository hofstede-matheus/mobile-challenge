package com.hofstedematheus.btg_mobilechallange.util.extensions

import java.text.NumberFormat

fun Float.toMoneyString(): String {
    val format: NumberFormat = NumberFormat.getInstance()
    format.maximumFractionDigits = 2

    return format.format(this)
}