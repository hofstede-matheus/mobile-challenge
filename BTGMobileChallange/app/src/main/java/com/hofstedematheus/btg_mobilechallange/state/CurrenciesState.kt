package com.hofstedematheus.btg_mobilechallange.state

import com.hofstedematheus.btg_mobilechallange.model.Currency

data class CurrenciesState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val error: String = "",
    val currencies: List<Currency> = listOf(),
)
