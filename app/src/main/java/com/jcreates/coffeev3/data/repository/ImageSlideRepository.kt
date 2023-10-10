package com.jcreates.coffeev3.data.repository

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.dao.SlideImageDao
import com.jcreates.coffeev3.data.entity.SlideImage

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject

class ImageSlideRepository(application: Application) {
    private val dao: SlideImageDao
    private val all: LiveData<List<SlideImage>>
    private val all2: LiveData<List<SlideImage>>
    private val application: Application

    init {
        val db: AppDatabase = AppDatabase.getDatabase(application)
        dao = db.getImageSlideDao()
        all = dao.fetchAll()
        all2 = dao.fetchAll2()
        this.application = application
    }
    fun fetchAll2(): LiveData<List<SlideImage>> {
        return all2
    }
    fun fetchAll(): LiveData<List<SlideImage>> {
        return all
    }
    fun getcount(): Cursor? {
        return dao.getCount()
    }
    fun getListPopular(): Cursor? {
        return dao.get_list()
    }


    fun insertImageSlide(response: JSONArray) {
        Log.d("SLIDE",response.toString())
        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                Log.d("SLIDE  ", "START")
                dao.deleteAll()
                dao.deleteAllSequence()
                val items: ArrayList<SlideImage> = ArrayList<SlideImage>()
                for (i in 0 until response.length()) {
                    val data: JSONObject = response.getJSONObject(i)
                    val item = SlideImage(0,
                        data.getString("slide_id"),
                        data.getString("slide_image"),
                        data.getString("record_date"),
                        data.getString("status")
                    )
                    items.add(item)
                }
                dao.insertAll(items)
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {
                    Log.d("SLIDE  ", "COMPLETE")
                }

                override fun onError(e: Throwable) {
                    Log.e("SLIDE   ", e.message!!)
                }
            })
    }








}