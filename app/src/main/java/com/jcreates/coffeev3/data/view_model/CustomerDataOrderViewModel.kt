package com.joey.noteapplication.data.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.entity.CustomerDataOrder
import com.jcreates.coffeev3.data.repository.CustomerDataOrderRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CustomerDataOrderViewModel(application: Application) : AndroidViewModel(application) {


    val allnotes: LiveData<List<CustomerDataOrder>>
    val repository: CustomerDataOrderRepository

    init {
        val dao = AppDatabase.getDatabase(application).getCustomerDataDao()
        repository= CustomerDataOrderRepository(application)
        allnotes = repository.fetchAll()

    }

    fun getTOTALSubmit(): LiveData<List<Integer>> {
        return repository.totalCart()
    }
    fun getTotalPrice(): LiveData<List<Integer>> {
        return repository.totalPrice()
    }

    fun insert(item: CustomerDataOrder?) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(item)
    }

    fun delete ()= viewModelScope.launch(Dispatchers.IO) {
        repository.delete()
    }
//    fun update(latitude:String?) = viewModelScope.launch(Dispatchers.IO){
//        repository.updateCustomerData(latitude)
//    }
}