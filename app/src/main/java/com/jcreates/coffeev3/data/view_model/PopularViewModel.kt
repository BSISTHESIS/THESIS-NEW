package com.joey.noteapplication.data.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.entity.Popular
import com.jcreates.coffeev3.data.repository.PopularRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray


class PopularViewModel(application: Application) : AndroidViewModel(application) {


    val allnotes: LiveData<List<Popular>>
    val allnotes2: LiveData<List<Popular>>

    val repository: PopularRepository


    init {
        val dao = AppDatabase.getDatabase(application).getPopularDao()
        repository= PopularRepository(application)
        allnotes = repository.fetchAll()
        allnotes2 = repository.fetchAll2()
    }


    fun insert(response: JSONArray) = viewModelScope.launch(Dispatchers.IO){
        repository.insertPopularItem(response)
    }


}