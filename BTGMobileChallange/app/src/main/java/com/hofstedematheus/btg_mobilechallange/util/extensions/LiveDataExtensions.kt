package com.hofstedematheus.btg_mobilechallange.util.extensions

import androidx.lifecycle.MutableLiveData

inline fun <T> MutableLiveData<T>.reduce(block: (T) -> T?) {
    value = value?.let(block)
}