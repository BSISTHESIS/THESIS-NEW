package com.jcreates.coffeev3.data.repository

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.dao.SalesDao
import com.jcreates.coffeev3.data.entity.CashierListOrder
import com.jcreates.coffeev3.data.entity.SalesList

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray
import org.json.JSONObject

class SalesRepository(application: Application) {
    private val dao: SalesDao
    private val all: LiveData<List<SalesList>>
    private val all2: LiveData<List<SalesList>>
    private val application: Application

    init {
        val db: AppDatabase = AppDatabase.getDatabase(application)
        dao = db.getSalesDao()
        all = dao.fetchAll()
        all2 = dao.fetchAll2()
        this.application = application
    }
    fun fetchAll2(): LiveData<List<SalesList>> {
        return all2
    }
    fun fetchAll(): LiveData<List<SalesList>> {
        return all
    }
    fun getcount(): Cursor? {
        return dao.getCount()
    }
    fun getListPopular(): Cursor? {
        return dao.get_list()
    }
    fun getGrandTotal(): Cursor? {
        return dao.getGrandTotal()
    }



    fun searchDatabase(search: String): Flow<List<SalesList>> {
        return  dao.searchDatabase(search)
    }

    fun insertSaleList(response: JSONArray) {
        Log.d("SALESlist",response.toString())
        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                Log.d("SALESlist  ", "START")
                dao.deleteAll()
                dao.deleteAllSequence()
                val items: ArrayList<SalesList> = ArrayList<SalesList>()
                for (i in 0 until response.length()) {
                    val data: JSONObject = response.getJSONObject(i)
                    val item = SalesList(0,
                        data.getString("order_no"),
                        data.getString("order_datetime"),
                        data.getString("order_status"),
                        data.getString("payment"),
                        data.getString("money"),
                        data.getString("final_change"),
                        data.getString("name_order"),
                        data.getString("scheduled_date"),

                        )
                    items.add(item)
                }
                dao.insertAll(items)
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {
                    Log.d("SalesList  ", "COMPLETE")
                }

                override fun onError(e: Throwable) {
                    Log.e("CDataListOrdersss   ", e.message!!)
                }
            })
    }







}