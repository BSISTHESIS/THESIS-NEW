package com.joey.noteapplication.data.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.entity.Cashier
import com.jcreates.coffeev3.data.entity.CashierListOrder
import com.jcreates.coffeev3.data.entity.Popular
import com.jcreates.coffeev3.data.entity.SalesList
import com.jcreates.coffeev3.data.repository.CashierListOrderRepository
import com.jcreates.coffeev3.data.repository.CashierRepository
import com.jcreates.coffeev3.data.repository.PopularRepository
import com.jcreates.coffeev3.data.repository.SalesRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray


class SalesViewModel(application: Application) : AndroidViewModel(application) {


    val allnotes: LiveData<List<SalesList>>
    val allnotes2: LiveData<List<SalesList>>

    val repository: SalesRepository


    init {
        val dao = AppDatabase.getDatabase(application).getPopularDao()
        repository= SalesRepository(application)
        allnotes = repository.fetchAll()
        allnotes2 = repository.fetchAll2()
    }



    fun searchDatabase(search:String):LiveData<List<SalesList>>{
        return  repository.searchDatabase(search).asLiveData()
    }
}