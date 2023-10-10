package com.jcreates.coffeev3.data.repository

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.dao.AddonsDao
import com.jcreates.coffeev3.data.entity.Addons



import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject

class AddOnsRepository(application: Application) {
    private val dao: AddonsDao
    private val all: LiveData<List<Addons>>
    private val all2: LiveData<List<Addons>>
    private val application: Application

    init {
        val db: AppDatabase = AppDatabase.getDatabase(application)
        dao = db.getAddsOnDao()
        all = dao.fetchAll()
        all2 = dao.fetchAll2()
        this.application = application
    }
    fun fetchAll2(): LiveData<List<Addons>> {
        return all2
    }
    fun fetchAll(): LiveData<List<Addons>> {
        return all
    }
    fun getcount(): Cursor? {
        return dao.getCount()
    }
    fun getListCoffee(): Cursor? {
        return dao.get_list()
    }

    fun getListProduct(list:String): Cursor? {
        return dao.get_lists(list)
    }

    fun getListAddCount(list:String): Cursor? {
        return dao.get_lists_count(list)
    }
    fun insertAddons(response: JSONArray) {
        Log.d("WAFFLE",response.toString())
        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                Log.d("ADDONS  ", "START")
                dao.deleteAll()
                dao.deleteAllSequence()
                val items: ArrayList<Addons> = ArrayList<Addons>()
                for (i in 0 until response.length()) {
                    val data: JSONObject = response.getJSONObject(i)
                    val item = Addons(0,
                        data.getString("id"),
                        data.getString("add_ons_name"),
                        data.getString("add_ons_price"),
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
                    Log.d("Addons  ", "COMPLETE")
                }

                override fun onError(e: Throwable) {
                    Log.e("Addons   ", e.message!!)
                }
            })
    }








}