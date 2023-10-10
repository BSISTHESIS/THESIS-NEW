package com.jcreates.coffeev3.data.repository

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.dao.CashierDao
import com.jcreates.coffeev3.data.dao.CashierListOrderDao
import com.jcreates.coffeev3.data.dao.PopularDao
import com.jcreates.coffeev3.data.entity.Cashier
import com.jcreates.coffeev3.data.entity.CashierListOrder
import com.jcreates.coffeev3.data.entity.Popular

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray
import org.json.JSONObject

class CashierListOrderRepository(application: Application) {
    private val dao: CashierListOrderDao
    private val all: LiveData<List<CashierListOrder>>
    private val all2: LiveData<List<CashierListOrder>>
    private val application: Application

    init {
        val db: AppDatabase = AppDatabase.getDatabase(application)
        dao = db.getCashierListOrderDao()
        all = dao.fetchAll()
        all2 = dao.fetchAll2()
        this.application = application
    }
    fun fetchAll2(): LiveData<List<CashierListOrder>> {
        return all2
    }
    fun fetchAll(): LiveData<List<CashierListOrder>> {
        return all
    }
    fun getcount(): Cursor? {
        return dao.getCount()
    }
    fun getListPopular(): Cursor? {
        return dao.get_list()
    }


    fun insertCashierListOrder(response: JSONArray) {
        Log.d("CDataListOrdersss",response.toString())
        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                Log.d("CDataListOrdersss  ", "START")
                dao.deleteAll()
                dao.deleteAllSequence()
                val items: ArrayList<CashierListOrder> = ArrayList<CashierListOrder>()
                for (i in 0 until response.length()) {
                    val data: JSONObject = response.getJSONObject(i)
                    val item = CashierListOrder(0,
                        data.getString("order_no"),
                        data.getString("name_order"),
                        data.getString("order_level"),
                        data.getString("order_status"),
                        data.getString("payment"),
                        data.getString("money"),
                        data.getString("final_change")
                        )
                    items.add(item)
                }
                dao.insertAll(items)
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {
                    Log.d("CDataListOrdersss  ", "COMPLETE")
                }

                override fun onError(e: Throwable) {
                    Log.e("CDataListOrdersss   ", e.message!!)
                }
            })
    }



    fun searchDatabase(search: String): Flow<List<CashierListOrder>> {
        return  dao.searchDatabase(search)
    }




}