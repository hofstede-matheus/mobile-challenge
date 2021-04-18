package com.hofstedematheus.btg_mobilechallange.services.currencyapi

import com.hofstedematheus.btg_mobilechallange.services.currencyapi.dao.GetCurrenciesResponse
import com.hofstedematheus.btg_mobilechallange.services.currencyapi.dao.GetLiveCurrenciesResponse
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import retrofit2.Response

@KoinApiExtension
class CurrencyApiService: KoinComponent {
    private val api: CurrencyApi = get()

    suspend fun getCurrencies(): Response<GetCurrenciesResponse> = api.getCurrencies()

    suspend fun getLiveCurrencies(): Response<GetLiveCurrenciesResponse> = api.getLiveCurrencies()

}