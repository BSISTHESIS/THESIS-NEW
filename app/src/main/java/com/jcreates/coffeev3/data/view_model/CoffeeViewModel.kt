package com.joey.noteapplication.data.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.entity.Coffee
import com.jcreates.coffeev3.data.repository.CoffeeRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray


class CoffeeViewModel(application: Application) : AndroidViewModel(application) {


    val allnotes: LiveData<List<Coffee>>
    val allnotes2: LiveData<List<Coffee>>
    val repository: CoffeeRepository


    init {
        val dao = AppDatabase.getDatabase(application).getPopularDao()
        repository= CoffeeRepository(application)
        allnotes = repository.fetchAll()
        allnotes2 = repository.fetchAll2()
    }


    fun insert(response: JSONArray) = viewModelScope.launch(Dispatchers.IO){
        repository.insertCoffeeItem(response)
    }


}