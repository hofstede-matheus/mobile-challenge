package com.hofstedematheus.btg_mobilechallange.di

import com.hofstedematheus.btg_mobilechallange.repository.CurrencyConverterRepository
import com.hofstedematheus.btg_mobilechallange.repository.datasources.CurrencyConverterDataSource
import com.hofstedematheus.btg_mobilechallange.repository.datasources.MockedCurrencyConverterDataSource
import com.hofstedematheus.btg_mobilechallange.scenes.currencyconverter.CurrencyConverterViewModel
import com.hofstedematheus.btg_mobilechallange.services.currencyapi.CurrencyApiService
import com.hofstedematheus.btg_mobilechallange.services.mock.MockedApiService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val currencyConverterModule = module {
    single { MockedApiService() }
    single { CurrencyApiService() }

    single<CurrencyConverterRepository> { CurrencyConverterDataSource() }

    viewModel {
        CurrencyConverterViewModel()
    }


}