package com.joey.noteapplication.data.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.entity.Cashier
import com.jcreates.coffeev3.data.entity.Popular
import com.jcreates.coffeev3.data.repository.CashierRepository
import com.jcreates.coffeev3.data.repository.PopularRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray


class CashierViewModel(application: Application) : AndroidViewModel(application) {


    val allnotes: LiveData<List<Cashier>>
    val allnotes2: LiveData<List<Cashier>>

    val repository: CashierRepository


    init {
        val dao = AppDatabase.getDatabase(application).getPopularDao()
        repository= CashierRepository(application)
        allnotes = repository.fetchAll()
        allnotes2 = repository.fetchAll2()
    }


    fun insert(response: JSONArray) = viewModelScope.launch(Dispatchers.IO){
        repository.insertCashier(response)
    }


}