package com.hofstedematheus.btg_mobilechallange.scenes.currencyconverter

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hofstedematheus.btg_mobilechallange.R
import com.hofstedematheus.btg_mobilechallange.databinding.ActivityCurrencyConverterBinding
import com.hofstedematheus.btg_mobilechallange.mock.currencies
import com.hofstedematheus.btg_mobilechallange.util.Status
import org.koin.androidx.viewmodel.ext.android.viewModel



class CurrencyConverterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCurrencyConverterBinding
    private val viewModel: CurrencyConverterViewModel by viewModel()
    private lateinit var currenciesAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCurrencyConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupViewModel()

        initUI()
    }

    private fun initUI() {
        currenciesAdapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            viewModel.state.value?.data?.currencies?.map { "${it.code} - ${it.name}" } ?: listOf()
        )
        binding.fromCurrencyEditText.setAdapter(currenciesAdapter)
        binding.toCurrencyEditText.setAdapter(currenciesAdapter)
    }

    private fun setupViewModel() {
        viewModel.apply {
            state.observe(
                this@CurrencyConverterActivity,
                { state ->
                    when (state.status) {
                        Status.ERROR -> {
                            Toast.makeText(this@CurrencyConverterActivity, "Erro", Toast.LENGTH_SHORT).show()
                        }
                        Status.SUCCESS -> {
                            Toast.makeText(this@CurrencyConverterActivity, state.data?.currencies.toString(), Toast.LENGTH_SHORT).show()
                        }
                        Status.LOADING -> {
                            Toast.makeText(this@CurrencyConverterActivity, "Loading", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }
    }

    private fun setupListeners() {

    }
}