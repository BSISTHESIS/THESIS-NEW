package com.joey.noteapplication.data.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.entity.Drinks
import com.jcreates.houseofcoffee.data.repository.DrinksRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray


class DrinksViewModel(application: Application) : AndroidViewModel(application) {


    val allnotes: LiveData<List<Drinks>>
    val allnotes2: LiveData<List<Drinks>>
    val repository: DrinksRepository


    init {
        val dao = AppDatabase.getDatabase(application).getPopularDao()
        repository= DrinksRepository(application)
        allnotes = repository.fetchAll()
        allnotes2 = repository.fetchAll2()
    }


    fun insert(response: JSONArray) = viewModelScope.launch(Dispatchers.IO){
        repository.insertDrinksItem(response)
    }


}