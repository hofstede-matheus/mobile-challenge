package com.hofstedematheus.btg_mobilechallange.di

import android.content.Context
import android.content.SharedPreferences
import com.hofstedematheus.btg_mobilechallange.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val androidModule = module {
    single<SharedPreferences> { androidApplication().getSharedPreferences(BuildConfig.PREFERENCES_NAME, Context.MODE_PRIVATE) }
}