package com.hofstedematheus.btg_mobilechallange.repository.datasources

import android.content.SharedPreferences
import com.google.gson.Gson
import com.hofstedematheus.btg_mobilechallange.constants.CACHE_LAST_UPDATED_ON
import com.hofstedematheus.btg_mobilechallange.constants.CURRENCIES_CACHE
import com.hofstedematheus.btg_mobilechallange.constants.UNKNOWN_ERROR
import com.hofstedematheus.btg_mobilechallange.model.Currency
import com.hofstedematheus.btg_mobilechallange.repository.CurrencyConverterRepository
import com.hofstedematheus.btg_mobilechallange.services.currencyapi.CurrencyApiService
import com.hofstedematheus.btg_mobilechallange.services.currencyapi.dao.GetCurrenciesResponse
import com.hofstedematheus.btg_mobilechallange.util.extensions.format
import com.hofstedematheus.btg_mobilechallange.util.extensions.toErrorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import org.koin.java.KoinJavaComponent.inject
import java.time.format.DateTimeFormatter
import java.util.*

@KoinApiExtension
class CurrencyConverterDataSource : CurrencyConverterRepository {
    private val api by inject(CurrencyApiService::class.java)
    private val preferences by inject(SharedPreferences::class.java)


    override suspend fun getCurrencies(forceRefresh: Boolean): List<Currency> {
        if (forceRefresh) {
            val response = api.getCurrencies()
            if(response.isSuccessful) {
                response.body()?.let { getCurrenciesResponse ->
                    with(preferences.edit()) {
                        putString(CURRENCIES_CACHE, Gson().toJson(getCurrenciesResponse.currencies))
                        putString(CACHE_LAST_UPDATED_ON, Date().format())
                        apply()
                    }
                    return getCurrenciesResponse.currencies.map { Currency(it.key, it.value) }
                }
            }
            response.errorBody()?.let {
                throw Exception(it.toErrorMessage())
            }
            throw Exception(UNKNOWN_ERROR)
        } else {
            val cachedCurrencies = preferences.getString(CURRENCIES_CACHE, "")
            cachedCurrencies?.let {
                if (cachedCurrencies.isNotEmpty()) {
                    val cachedCurrenciesSerialized = withContext(Dispatchers.IO) {
                        Gson().fromJson(cachedCurrencies, GetCurrenciesResponse::class.java)
                    }
                    return cachedCurrenciesSerialized.currencies.map { Currency(it.key, it.value) }
                }
            }
            return getCurrencies(true)
        }
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