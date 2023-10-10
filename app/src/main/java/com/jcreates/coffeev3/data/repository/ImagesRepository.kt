package com.jcreates.coffeev3.data.repository

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.dao.ImagesDao
import com.jcreates.coffeev3.data.entity.Images
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class ImagesRepository(application: Application) {
    private val dao: ImagesDao
    private val all: LiveData<List<Images>>
    private val application: Application

    init {
        val db: AppDatabase = AppDatabase.getDatabase(application)
        dao = db.getImagesDao()
        all = dao.fetchAll()
        this.application = application
    }

    fun fetchAll(): LiveData<List<Images>> {
        return all
    }


    fun getImageUpload(): Cursor? {
        return dao.getImages()
    }

    fun insertImages(item: Images?) {
        io.reactivex.rxjava3.core.Completable.fromAction {
            Log.d("Images", "START")
            dao.deleteAll()
            dao.deleteAllSequence()
            dao.insert(item!!)
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
            .subscribe(object : io.reactivex.rxjava3.core.CompletableObserver {
                override fun onSubscribe(d: io.reactivex.rxjava3.disposables.Disposable) {}
                override fun onComplete() {
                    Log.d("Images ", "COMPLETE")
                }

                override fun onError(e: Throwable) {
                    Log.e("Images ", e.message!!)
                }
            })
    }




}