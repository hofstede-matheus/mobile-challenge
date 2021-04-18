package com.hofstedematheus.btg_mobilechallange.util.extensions

import com.google.gson.Gson
import com.hofstedematheus.btg_mobilechallange.services.currencyapi.dao.GetCurrenciesErrorResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import kotlin.coroutines.CoroutineContext

suspend fun ResponseBody.toErrorMessage(): String {
    val error = withContext(Dispatchers.IO) {
        Gson().fromJson(this@toErrorMessage.string(), GetCurrenciesErrorResponse::class.java)
    }
    return error.error.info
}