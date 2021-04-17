package com.hofstedematheus.btg_mobilechallange.repository

import com.hofstedematheus.btg_mobilechallange.model.Currency

interface CurrencyConverterRepository {
    suspend fun getCurrencies(): List<Currency>
    suspend fun convertCurrency(dolarValue: Float): Float
}