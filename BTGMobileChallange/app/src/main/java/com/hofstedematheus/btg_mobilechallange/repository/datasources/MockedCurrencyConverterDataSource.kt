package com.hofstedematheus.btg_mobilechallange.repository.datasources

import com.hofstedematheus.btg_mobilechallange.model.Currency
import com.hofstedematheus.btg_mobilechallange.repository.CurrencyConverterRepository
import com.hofstedematheus.btg_mobilechallange.services.MockedApiService
import org.koin.core.component.KoinApiExtension
import org.koin.java.KoinJavaComponent.inject

@KoinApiExtension
class MockedCurrencyConverterDataSource: CurrencyConverterRepository {
    private val service by inject(MockedApiService::class.java)

    override suspend fun getCurrencies(): List<Currency> {
        return service.getCurrencies()
    }

    override suspend fun convertCurrency(fromCurrency: String, fromCurrencyValue: Float, toCurrency: String): Float {
        return service.convertCurrency(fromCurrencyValue)
    }
}