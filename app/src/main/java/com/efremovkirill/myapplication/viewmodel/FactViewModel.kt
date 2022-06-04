package com.efremovkirill.myapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efremovkirill.myapplication.data.FactDatabase
import com.efremovkirill.myapplication.data.FactModel
import com.efremovkirill.myapplication.data.FactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FactViewModel(application: Application): ViewModel() {

    private val repository: FactRepository
    val factData: LiveData<List<FactModel>>

    init {
        val factDao = FactDatabase.getDatabase(application).factDao()
        repository = FactRepository(factDao)
        factData = repository.factData
    }

    fun insertFact(fact: FactModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFact(fact)
        }
    }

    fun getRandomFact(): FactModel? {
        val fact = repository.getRandomFact()
        Log.d("Test", "Tryna get fact $fact")
        return fact
    }

    fun factAvailable(id: String): Boolean {
        return repository.getFact(id) != null
    }

    fun dropTable() {
        repository.dropTable()
    }
}