package com.joey.noteapplication.data.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.entity.SlideImage
import com.jcreates.coffeev3.data.repository.ImageSlideRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray


class ImageViewModel(application: Application) : AndroidViewModel(application) {


    val allnotes: LiveData<List<SlideImage>>
    val allnotes2: LiveData<List<SlideImage>>

    val repository: ImageSlideRepository


    init {
        val dao = AppDatabase.getDatabase(application).getPopularDao()
        repository= ImageSlideRepository(application)
        allnotes = repository.fetchAll()
        allnotes2 = repository.fetchAll2()
    }


    fun insert(response: JSONArray) = viewModelScope.launch(Dispatchers.IO){
        repository.insertImageSlide(response)
    }


}