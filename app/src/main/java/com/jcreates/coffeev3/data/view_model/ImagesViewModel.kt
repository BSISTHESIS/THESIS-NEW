package com.jcreates.coffeev3.data.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jcreates.coffeev3.data.entity.Images
import com.jcreates.coffeev3.data.repository.ImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImagesViewModel(application: Application) : AndroidViewModel(application) {


    val allnotes: LiveData<List<Images>>
    val repository:ImagesRepository


    init {

        repository= ImagesRepository(application)
        allnotes = repository.fetchAll()
    }


    fun insertImages(item: Images?) = viewModelScope.launch(Dispatchers.IO){
        repository.insertImages(item)
    }


}