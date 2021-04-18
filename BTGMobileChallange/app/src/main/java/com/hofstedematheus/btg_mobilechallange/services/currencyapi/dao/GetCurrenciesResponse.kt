package com.hofstedematheus.btg_mobilechallange.services.currencyapi.dao

data class GetCurrenciesResponse (
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val currencies: Map<String, String>
)

