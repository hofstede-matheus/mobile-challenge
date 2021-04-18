package com.hofstedematheus.btg_mobilechallange.di

import com.hofstedematheus.btg_mobilechallange.BuildConfig
import com.hofstedematheus.btg_mobilechallange.services.currencyapi.CurrencyApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single<CurrencyApi> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)
    }
}