package com.hofstedematheus.btg_mobilechallange.util.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Date.format(): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
    return formatter.format(this)
}