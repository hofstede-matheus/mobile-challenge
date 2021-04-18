package com.hofstedematheus.btg_mobilechallange.services.currencyapi

import com.hofstedematheus.btg_mobilechallange.BuildConfig
import com.hofstedematheus.btg_mobilechallange.services.currencyapi.dao.GetCurrenciesResponse
import com.hofstedematheus.btg_mobilechallange.services.currencyapi.dao.GetLiveCurrenciesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("list")
    suspend fun getCurrencies(@Query("access_key") access_key: String? = BuildConfig.API_KEY ): Response<GetCurrenciesResponse>

    @GET("live")
    suspend fun getLiveCurrencies(@Query("access_key") access_key: String? = BuildConfig.API_KEY ): Response<GetLiveCurrenciesResponse>

}