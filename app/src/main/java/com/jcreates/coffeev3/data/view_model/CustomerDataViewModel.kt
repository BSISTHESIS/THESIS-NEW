package com.joey.noteapplication.data.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.entity.CustomerData
import com.jcreates.coffeev3.data.repository.CustomerDataRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CustomerDataViewModel(application: Application) : AndroidViewModel(application) {


    val allnotes: LiveData<List<CustomerData>>
    val repository: CustomerDataRepository


    init {
        val dao = AppDatabase.getDatabase(application).getCustomerDataDao()
        repository= CustomerDataRepository(application)
        allnotes = repository.fetchAll()
    }


    fun insert(item: CustomerData?) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(item)
    }

    fun update(latitude:String?) = viewModelScope.launch(Dispatchers.IO){
        repository.updateCustomerData(latitude)
    }
}