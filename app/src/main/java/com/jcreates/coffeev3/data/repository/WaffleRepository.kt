package com.jcreates.coffeev3.data.repository

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.dao.WaffleDao
import com.jcreates.coffeev3.data.entity.Waffle


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject

class WaffleRepository(application: Application) {
    private val dao: WaffleDao
    private val all: LiveData<List<Waffle>>
    private val all2: LiveData<List<Waffle>>
    private val application: Application

    init {
        val db: AppDatabase = AppDatabase.getDatabase(application)
        dao = db.getWaffleDao()
        all = dao.fetchAll()
        all2 = dao.fetchAll2()
        this.application = application
    }
    fun fetchAll2(): LiveData<List<Waffle>> {
        return all2
    }
    fun fetchAll(): LiveData<List<Waffle>> {
        return all
    }
    fun getcount(): Cursor? {
        return dao.getCount()
    }
    fun getListCoffee(): Cursor? {
        return dao.get_list()
    }


    fun insertWaffleItem(response: JSONArray) {
        Log.d("WAFFLE",response.toString())
        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                Log.d("WAFFLE  ", "START")
                dao.deleteAll()
                dao.deleteAllSequence()
                val items: ArrayList<Waffle> = ArrayList<Waffle>()
                for (i in 0 until response.length()) {
                    val data: JSONObject = response.getJSONObject(i)
                    val item = Waffle(0,
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
                    Log.d("WAFFLE  ", "COMPLETE")
                }

                override fun onError(e: Throwable) {
                    Log.e("WAFFLE   ", e.message!!)
                }
            })
    }








}