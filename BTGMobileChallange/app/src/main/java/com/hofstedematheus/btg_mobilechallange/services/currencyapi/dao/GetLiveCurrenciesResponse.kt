package com.hofstedematheus.btg_mobilechallange.services.currencyapi.dao

data class GetLiveCurrenciesResponse(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val timestamp: Long,
    val source: String,
    val quotes: Map<String, Float>
)
