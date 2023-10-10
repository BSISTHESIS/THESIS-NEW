package com.joey.noteapplication.data.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.entity.Waffle
import com.jcreates.coffeev3.data.repository.WaffleRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray


class WaffleViewModel(application: Application) : AndroidViewModel(application) {


    val allnotes: LiveData<List<Waffle>>
    val allnotes2: LiveData<List<Waffle>>
    val repository: WaffleRepository


    init {
        val dao = AppDatabase.getDatabase(application).getPopularDao()
        repository= WaffleRepository(application)
        allnotes = repository.fetchAll()
        allnotes2 = repository.fetchAll2()
    }


    fun insert(response: JSONArray) = viewModelScope.launch(Dispatchers.IO){
        repository.insertWaffleItem(response)
    }


}