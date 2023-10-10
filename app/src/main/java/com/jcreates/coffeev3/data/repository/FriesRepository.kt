package com.jcreates.coffeev3.data.repository

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.dao.FriesDao
import com.jcreates.coffeev3.data.entity.Fries


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject

class FriesRepository(application: Application) {
    private val dao: FriesDao
    private val all: LiveData<List<Fries>>
    private val all2: LiveData<List<Fries>>
    private val application: Application

    init {
        val db: AppDatabase = AppDatabase.getDatabase(application)
        dao = db.getFriesDao()
        all = dao.fetchAll()
        all2 = dao.fetchAll2()
        this.application = application
    }
    fun fetchAll2(): LiveData<List<Fries>> {
        return all2
    }
    fun fetchAll(): LiveData<List<Fries>> {
        return all
    }
    fun getcount(): Cursor? {
        return dao.getCount()
    }
    fun getFriesList(): Cursor? {
        return dao.get_list()
    }


    fun insertFriesItem(response: JSONArray) {
        Log.d("FRIES",response.toString())
        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                Log.d("FRIES  ", "START")
                dao.deleteAll()
                dao.deleteAllSequence()
                val items: ArrayList<Fries> = ArrayList<Fries>()
                for (i in 0 until response.length()) {
                    val data: JSONObject = response.getJSONObject(i)
                    val item = Fries(0,
                        data.getString("product_id"),
                        data.getString("product_name"),
                        data.getString("product_price"),
                        data.getString("product_image"),
                        data.getString("product_record_datetime"),
                        data.getString("product_status"),
                        data.getString("product_code"),
                    )
                    items.add(item)
                }
                dao.insertAll(items)
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {
                    Log.d("FRIES  ", "COMPLETE")
                }

                override fun onError(e: Throwable) {
                    Log.e("FRIES   ", e.message!!)
                }
            })
    }








}