package com.hofstedematheus.btg_mobilechallange.services.mock

import com.hofstedematheus.btg_mobilechallange.mock.currencies
import com.hofstedematheus.btg_mobilechallange.model.Currency
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent

@KoinApiExtension
class MockedApiService: KoinComponent {
    suspend fun getCurrencies(): List<Currency> {
        return currencies
    }

    suspend fun convertCurrency(dolarValue: Float): Float {
        return dolarValue * 2
    }
}