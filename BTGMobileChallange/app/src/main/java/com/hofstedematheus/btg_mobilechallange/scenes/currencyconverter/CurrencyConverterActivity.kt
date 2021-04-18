package com.hofstedematheus.btg_mobilechallange.scenes.currencyconverter

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.hofstedematheus.btg_mobilechallange.R
import com.hofstedematheus.btg_mobilechallange.constants.CACHE_LAST_UPDATED_ON
import com.hofstedematheus.btg_mobilechallange.constants.CURRENCIES_CACHE
import com.hofstedematheus.btg_mobilechallange.databinding.ActivityCurrencyConverterBinding
import com.hofstedematheus.btg_mobilechallange.util.extensions.isVisibleIf
import com.hofstedematheus.btg_mobilechallange.util.extensions.toMoneyString
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.inject


class CurrencyConverterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCurrencyConverterBinding
    private val viewModel: CurrencyConverterViewModel by viewModel()
    private val preferences by inject(SharedPreferences::class.java)
    private lateinit var currenciesAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCurrencyConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupViewModel()

        initUI()
        viewModel.getCurrencies()
    }

    private fun initUI() {
        currenciesAdapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            viewModel.currenciesStateLiveData.value?.currencies?.map { "${it.code} - ${it.name}" } ?: listOf()
        )
        binding.fromCurrencyEditText.setAdapter(currenciesAdapter)
        binding.toCurrencyEditText.setAdapter(currenciesAdapter)

        binding.fromCurrencyValueEditText.setText(viewModel.conversionStateLiveData.value?.currencyFromValue.toString())

        if (preferences.contains(CACHE_LAST_UPDATED_ON)) binding.labelLastRefresh.text =
            preferences.getString(CACHE_LAST_UPDATED_ON, "")

    }

    private fun setupViewModel() {
        viewModel.apply {
            currenciesStateLiveData.observe(
                this@CurrencyConverterActivity,
                { currenciesState ->
                    currenciesAdapter = ArrayAdapter(
                        this@CurrencyConverterActivity,
                        R.layout.support_simple_spinner_dropdown_item,
                        currenciesState.currencies.map { "${it.code} - ${it.name}" }
                    )
                    binding.fromCurrencyEditText.setAdapter(currenciesAdapter)
                    binding.toCurrencyEditText.setAdapter(currenciesAdapter)

                    binding.loadingProgress isVisibleIf currenciesState.isLoading

                    if (currenciesState.hasError) Toast.makeText(this@CurrencyConverterActivity, currenciesState.error, Toast.LENGTH_SHORT).show()
                }
            )
            conversionStateLiveData.observe(
                this@CurrencyConverterActivity,
                { conversionState ->
                    binding.convertedValueTextView.text = conversionState.currencyConverted.toMoneyString()
                }
            )
        }
    }

    private fun setupListeners() {
        binding.fromCurrencyEditText.setOnItemClickListener { _, _, position, _ ->
            viewModel.currenciesStateLiveData.value?.let {
                viewModel.updateFromCurrency(it.currencies[position])
                viewModel.convertCurrency()
            }
        }
        binding.toCurrencyEditText.setOnItemClickListener { _, _, position, _ ->
            viewModel.currenciesStateLiveData.value?.let {
                viewModel.updateToCurrency(it.currencies[position])
                viewModel.convertCurrency()
            }
        }
        binding.fromCurrencyValueEditText.addTextChangedListener {
            val fromCurrencyValue = if (it.toString().isNotEmpty()) it.toString().toFloat() else 0F
            viewModel.updateFromCurrencyValue(fromCurrencyValue)
            viewModel.convertCurrency()
        }
        binding.refreshIconImageView.setOnClickListener {
            viewModel.getCurrencies(forceRefresh = true)
            binding.labelLastRefresh.text =
                preferences.getString(CACHE_LAST_UPDATED_ON, "")
        }
    }
}