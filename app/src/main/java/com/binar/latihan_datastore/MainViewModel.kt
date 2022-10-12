package com.binar.latihan_datastore

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MainViewModel(private val pref: CounterDataStoreManager): ViewModel() {

    private val _counter: MutableLiveData<Int> = MutableLiveData(0)
    val counter: LiveData<Int> get() = _counter

    fun saveDataStore(value: Int) {
        viewModelScope.launch {
            pref.setCounter(value)
        }
    }

    fun getDataStore(): LiveData<Int> {
        return pref.getCounter().asLiveData()
    }

    fun incrementCount() {
        _counter.value = _counter.value?.plus(1)
    }

    fun decrementCount() {
        _counter.value?.let {
            if (it > 0) {
                _counter.value = _counter.value?.minus(1)
            }
        }
    }
}