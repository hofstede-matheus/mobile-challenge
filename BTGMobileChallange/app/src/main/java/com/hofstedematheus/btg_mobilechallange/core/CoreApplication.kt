package com.hofstedematheus.btg_mobilechallange.core

import android.app.Application
import com.hofstedematheus.btg_mobilechallange.di.apiModule
import com.hofstedematheus.btg_mobilechallange.di.currencyConverterModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CoreApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CoreApplication)

            modules(
                listOf(
                    currencyConverterModule,
                    apiModule
                )
            )
        }
    }
}