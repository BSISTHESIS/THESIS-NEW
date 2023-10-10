package com.jcreates.coffeev3.data.repository

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.dao.PopularDao
import com.jcreates.coffeev3.data.entity.Popular

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject

class PopularRepository(application: Application) {
    private val dao: PopularDao
    private val all: LiveData<List<Popular>>
    private val all2: LiveData<List<Popular>>
    private val application: Application

    init {
        val db: AppDatabase = AppDatabase.getDatabase(application)
        dao = db.getPopularDao()
        all = dao.fetchAll()
        all2 = dao.fetchAll2()
        this.application = application
    }
    fun fetchAll2(): LiveData<List<Popular>> {
        return all2
    }
    fun fetchAll(): LiveData<List<Popular>> {
        return all
    }
    fun getcount(): Cursor? {
        return dao.getCount()
    }
    fun getListPopular(): Cursor? {
        return dao.get_list()
    }


    fun insertPopularItem(response: JSONArray) {
        Log.d("POPULAR",response.toString())
        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                Log.d("POPULAR  ", "START")
                dao.deleteAll()
                dao.deleteAllSequence()
                val items: ArrayList<Popular> = ArrayList<Popular>()
                for (i in 0 until response.length()) {
                    val data: JSONObject = response.getJSONObject(i)
                    val item = Popular(0,
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
                    Log.d("POPULAR  ", "COMPLETE")
                }

                override fun onError(e: Throwable) {
                    Log.e("POPULAR   ", e.message!!)
                }
            })
    }








}