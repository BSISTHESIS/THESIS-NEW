package com.joey.noteapplication.data.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.entity.Addons
import com.jcreates.coffeev3.data.repository.AddOnsRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray


class AdsOnViewModel(application: Application) : AndroidViewModel(application) {


    val allnotes: LiveData<List<Addons>>
    val allnotes2: LiveData<List<Addons>>
    val repository: AddOnsRepository


    init {
        val dao = AppDatabase.getDatabase(application).getPopularDao()
        repository= AddOnsRepository(application)
        allnotes = repository.fetchAll()
        allnotes2 = repository.fetchAll2()
    }


    fun insert(response: JSONArray) = viewModelScope.launch(Dispatchers.IO){
        repository.insertAddons(response)
    }


}