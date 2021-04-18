package com.hofstedematheus.btg_mobilechallange.scenes.currencyconverter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hofstedematheus.btg_mobilechallange.model.Currency
import com.hofstedematheus.btg_mobilechallange.repository.CurrencyConverterRepository
import com.hofstedematheus.btg_mobilechallange.state.ConversionState
import com.hofstedematheus.btg_mobilechallange.state.CurrenciesState
import com.hofstedematheus.btg_mobilechallange.util.extensions.reduce
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.lang.Exception

class CurrencyConverterViewModel: ViewModel() {
    private val repository by inject(CurrencyConverterRepository::class.java)

    private val _currenciesStateLiveData = MutableLiveData(CurrenciesState())
    private val _conversionStateLiveData = MutableLiveData(ConversionState())

    val currenciesStateLiveData: LiveData<CurrenciesState>
        get() = _currenciesStateLiveData

    val conversionStateLiveData: LiveData<ConversionState>
        get() = _conversionStateLiveData

    fun getCurrencies(forceRefresh: Boolean = false) {
        _currenciesStateLiveData.reduce { CurrenciesState(isLoading = true) }

        viewModelScope.launch {
            try {
                val currencies = repository.getCurrencies(forceRefresh)
                _currenciesStateLiveData.reduce { CurrenciesState(currencies = currencies) }
            } catch (exception: Exception) {
                _currenciesStateLiveData.reduce { CurrenciesState(hasError = true, error = exception.message ?: "") }
            }
        }

    }

    fun convertCurrency() {
        val currencyFrom = _conversionStateLiveData.value?.currencyFrom
        val valueToBeConverted = _conversionStateLiveData.value?.currencyFromValue
        val currencyTo = _conversionStateLiveData.value?.currencyToBeConverted
        if (currencyFrom != null && currencyTo != null && valueToBeConverted != null) {
            viewModelScope.launch {
                try {
                    val convertedValue = repository.convertCurrency(currencyFrom.code, valueToBeConverted, currencyTo.code)
                    _conversionStateLiveData.reduce { _conversionStateLiveData.value?.copy(currencyConverted = convertedValue) }
                } catch (exception: Exception) {
                    _conversionStateLiveData.reduce { ConversionState(hasError = true, error = exception.message ?: "") }
                }
            }
        }
    }

    fun updateFromCurrency(currencyFrom: Currency) {
        _conversionStateLiveData.reduce {_conversionStateLiveData.value?.copy(currencyFrom = currencyFrom) }
    }

    fun updateFromCurrencyValue(currencyFromValue: Float) {
        _conversionStateLiveData.reduce { _conversionStateLiveData.value?.copy(currencyFromValue = currencyFromValue) }
    }

    fun updateToCurrency(currencyToBeConverted: Currency) {
        _conversionStateLiveData.reduce { _conversionStateLiveData.value?.copy(currencyToBeConverted = currencyToBeConverted) }
    }

}