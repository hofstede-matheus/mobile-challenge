package com.hofstedematheus.btg_mobilechallange

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.hofstedematheus.btg_mobilechallange.databinding.ActivityMainBinding
import com.hofstedematheus.btg_mobilechallange.mock.currencies


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.support_simple_spinner_dropdown_item, currencies.map { "${it.code} - ${it.name}" }
        )
        binding.fromCurrencyEditText.setAdapter(adapter)
        binding.toCurrencyEditText.setAdapter(adapter)
    }
}