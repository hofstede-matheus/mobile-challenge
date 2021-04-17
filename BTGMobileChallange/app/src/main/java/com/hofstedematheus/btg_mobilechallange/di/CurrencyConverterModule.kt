package com.hofstedematheus.btg_mobilechallange.di

import com.hofstedematheus.btg_mobilechallange.repository.CurrencyConverterRepository
import com.hofstedematheus.btg_mobilechallange.repository.datasources.MockedCurrencyConverterDataSource
import com.hofstedematheus.btg_mobilechallange.scenes.currencyconverter.CurrencyConverterViewModel
import com.hofstedematheus.btg_mobilechallange.services.MockedApiService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val currencyConverterModule = module {
    single { MockedApiService() }

    single<CurrencyConverterRepository> { MockedCurrencyConverterDataSource() }

    viewModel {
        CurrencyConverterViewModel()
    }


}