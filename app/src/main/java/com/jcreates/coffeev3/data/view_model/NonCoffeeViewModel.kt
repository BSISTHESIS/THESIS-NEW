package com.joey.noteapplication.data.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.entity.Non_Coffee
import com.jcreates.coffeev3.data.repository.NonCoffeeRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray


class NonCoffeeViewModel(application: Application) : AndroidViewModel(application) {


    val allnotes: LiveData<List<Non_Coffee>>
    val allnotes2: LiveData<List<Non_Coffee>>
    val repository: NonCoffeeRepository


    init {
        val dao = AppDatabase.getDatabase(application).getPopularDao()
        repository= NonCoffeeRepository(application)
        allnotes = repository.fetchAll()
        allnotes2 = repository.fetchAll2()
    }


    fun insert(response: JSONArray) = viewModelScope.launch(Dispatchers.IO){
        repository.insertNonCoffeeItem(response)
    }


}