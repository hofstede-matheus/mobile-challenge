package com.hofstedematheus.btg_mobilechallange.repository.datasources

import com.hofstedematheus.btg_mobilechallange.constants.UNKNOWN_ERROR
import com.hofstedematheus.btg_mobilechallange.model.Currency
import com.hofstedematheus.btg_mobilechallange.repository.CurrencyConverterRepository
import com.hofstedematheus.btg_mobilechallange.services.currencyapi.CurrencyApiService
import com.hofstedematheus.btg_mobilechallange.util.extensions.toErrorMessage
import org.koin.core.component.KoinApiExtension
import org.koin.java.KoinJavaComponent.inject

@KoinApiExtension
class CurrencyConverterDataSource : CurrencyConverterRepository {
    private val api by inject(CurrencyApiService::class.java)

    override suspend fun getCurrencies(): List<Currency> {
        val response = api.getCurrencies()
        if(response.isSuccessful) {
            response.body()?.let { getCurrenciesResponse ->
                return getCurrenciesResponse.currencies.map { Currency(it.key, it.value) }
            }
        }
        response.errorBody()?.let {
            throw Exception(it.toErrorMessage())
        }
        throw Exception(UNKNOWN_ERROR)
    }

    override suspend fun convertCurrency(
        fromCurrency: String,
        fromCurrencyValue: Float,
        toCurrency: String
    ): Float {
        val response = api.getLiveCurrencies()

        if(response.isSuccessful) {
            response.body()?.let { getLiveCurrenciesResponse ->

                if (fromCurrency != "USD") {
                    val fromValueExchangeRate = getLiveCurrenciesResponse.quotes["USD$fromCurrency"]
                    val toValueExchangeRate = getLiveCurrenciesResponse.quotes["USD$toCurrency"]
                    if (fromValueExchangeRate !=  null && toValueExchangeRate != null ) {
                        val valueInDolar = fromCurrencyValue / fromValueExchangeRate

                        return valueInDolar * toValueExchangeRate
                    }

                } else {
                    val toValueExchangeRate = getLiveCurrenciesResponse.quotes["USD$toCurrency"]
                    if (toValueExchangeRate != null ) return fromCurrencyValue * toValueExchangeRate
                }
                
            }
        }
        response.errorBody()?.let {
            throw Exception(it.toErrorMessage())
        }
        throw Exception(UNKNOWN_ERROR)
    }
}