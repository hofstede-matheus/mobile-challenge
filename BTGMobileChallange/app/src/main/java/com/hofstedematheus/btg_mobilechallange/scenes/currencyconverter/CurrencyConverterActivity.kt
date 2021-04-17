package com.hofstedematheus.btg_mobilechallange.scenes.currencyconverter

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.hofstedematheus.btg_mobilechallange.R
import com.hofstedematheus.btg_mobilechallange.databinding.ActivityCurrencyConverterBinding
import com.hofstedematheus.btg_mobilechallange.mock.currencies


class CurrencyConverterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCurrencyConverterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCurrencyConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.support_simple_spinner_dropdown_item, currencies.map { "${it.code} - ${it.name}" }
        )
        binding.fromCurrencyEditText.setAdapter(adapter)
        binding.toCurrencyEditText.setAdapter(adapter)
    }
}