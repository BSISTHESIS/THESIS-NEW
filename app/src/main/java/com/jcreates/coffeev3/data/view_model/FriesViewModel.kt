package com.joey.noteapplication.data.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.entity.Fries
import com.jcreates.coffeev3.data.repository.FriesRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray


class FriesViewModel(application: Application) : AndroidViewModel(application) {


    val allnotes: LiveData<List<Fries>>
    val allnotes2: LiveData<List<Fries>>
    val repository: FriesRepository


    init {
        val dao = AppDatabase.getDatabase(application).getPopularDao()
        repository= FriesRepository(application)
        allnotes2 = repository.fetchAll2()
        allnotes = repository.fetchAll()
    }


    fun insert(response: JSONArray) = viewModelScope.launch(Dispatchers.IO){
        repository.insertFriesItem(response)
    }


}