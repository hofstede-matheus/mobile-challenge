package com.hofstedematheus.btg_mobilechallange.state

import com.hofstedematheus.btg_mobilechallange.model.Currency

data class ConversionState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val error: String = "",

    val currencyFrom: Currency? = null,
    val currencyFromValue: Float = 0F,
    val currencyToBeConverted: Currency? = null,
    val currencyConverted: Float = 0F
)
