package com.hofstedematheus.btg_mobilechallange.util.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

infix fun View?.isVisibleIf(shouldShow: Boolean) {
    if (shouldShow) this?.visible()
    else this?.gone()
}

infix fun EditText?.isEnabledIf(shouldBeEnabled: Boolean) {
    this?.apply {
        isEnabled = shouldBeEnabled
    }
}

fun EditText.addDebouncedTextListener(
    debounceDuration: Long,
    lifecycle: Lifecycle,
    onChanged: (String) -> Unit
) = addTextChangedListener(object: TextWatcher {

    private var job: Job? = null
    private val coroutineScope: CoroutineScope = lifecycle.coroutineScope

    override fun afterTextChanged(newText: Editable?) { }

    override fun beforeTextChanged(newText: CharSequence?, start: Int, before: Int, count: Int) { }

    override fun onTextChanged(newText: CharSequence?, start: Int, before: Int, count: Int) {
        job?.cancel()
        job = coroutineScope.launch {
            newText?.let {
                delay(debounceDuration)
                onChanged(newText.toString())
            }
        }
    }
})