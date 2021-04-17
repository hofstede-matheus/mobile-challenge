package com.hofstedematheus.btg_mobilechallange.scenes.currencyconverter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hofstedematheus.btg_mobilechallange.model.CurrencyData
import com.hofstedematheus.btg_mobilechallange.repository.CurrencyConverterRepository
import com.hofstedematheus.btg_mobilechallange.util.Resource
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.lang.Exception

class CurrencyConverterViewModel: ViewModel() {
    private val repository by inject(CurrencyConverterRepository::class.java)

    private val _state = MutableLiveData<Resource<CurrencyData>>()

    val state: LiveData<Resource<CurrencyData>>
        get() = _state

    fun getCurrencies() {
        _state.postValue(Resource.loading(data = _state.value?.data))

        viewModelScope.launch {
            try {
                val currencies = repository.getCurrencies()
                _state.postValue(Resource.success(CurrencyData(currencies, _state.value?.data?.convertedCurrency ?: "")))
            } catch (exception: Exception) {
                _state.postValue(Resource.error(data = null, message = exception.message))
            }
        }

    }
}