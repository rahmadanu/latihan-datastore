package com.binar.latihan_datastore

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.binar.latihan_datastore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var pref: CounterDataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = CounterDataStoreManager(this)
        viewModel = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]

        setupView()
        setObserver()
    }

    private fun setObserver() {
        viewModel.apply {
            getDataStore().observe(this@MainActivity) {
                binding.tvNumber.text = it.toString()
            }
            counter.observe(this@MainActivity) { result ->
                binding.tvNumber.text = result.toString()
            }
        }
    }

    private fun setupView() {
        binding.apply {
            btnIncrease.setOnClickListener {
                incrementCount()
            }
            btnDecrease.setOnClickListener {
                decrementCount()
            }
            btnSet.setOnClickListener {
                val value = binding.tvNumber.text.toString().toInt()
                viewModel.saveDataStore(value)
            }
        }
    }

    private fun incrementCount() {
        viewModel.incrementCount()
    }

    private fun decrementCount() {
        viewModel.decrementCount()
    }

}