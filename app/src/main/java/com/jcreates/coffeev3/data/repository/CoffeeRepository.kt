package com.jcreates.coffeev3.data.repository

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.dao.CoffeeDao
import com.jcreates.coffeev3.data.entity.Coffee


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject

class CoffeeRepository(application: Application) {
    private val dao: CoffeeDao
    private val all: LiveData<List<Coffee>>
    private val all2: LiveData<List<Coffee>>
    private val application: Application

    init {
        val db: AppDatabase = AppDatabase.getDatabase(application)
        dao = db.getCoffeeDao()
        all = dao.fetchAll()
        all2 = dao.fetchAll2()
        this.application = application
    }
    fun fetchAll2(): LiveData<List<Coffee>> {
        return all2
    }
    fun fetchAll(): LiveData<List<Coffee>> {
        return all
    }
    fun getcount(): Cursor? {
        return dao.getCount()
    }
    fun getListCoffee(): Cursor? {
        return dao.get_list()
    }


    fun insertCoffeeItem(response: JSONArray) {
        Log.d("COFFEE",response.toString())
        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                Log.d("COFFEE  ", "START")
                dao.deleteAll()
                dao.deleteAllSequence()
                val items: ArrayList<Coffee> = ArrayList<Coffee>()
                for (i in 0 until response.length()) {
                    val data: JSONObject = response.getJSONObject(i)
                    val item = Coffee(0,
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
                    Log.d("COFFEE  ", "COMPLETE")
                }

                override fun onError(e: Throwable) {
                    Log.e("COFFEE   ", e.message!!)
                }
            })
    }








}