package com.jcreates.coffeev3.data.repository

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.dao.CashierDao
import com.jcreates.coffeev3.data.dao.PopularDao
import com.jcreates.coffeev3.data.entity.Cashier
import com.jcreates.coffeev3.data.entity.Popular

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject

class CashierRepository(application: Application) {
    private val dao: CashierDao
    private val all: LiveData<List<Cashier>>
    private val all2: LiveData<List<Cashier>>
    private val application: Application

    init {
        val db: AppDatabase = AppDatabase.getDatabase(application)
        dao = db.getCashierDao()
        all = dao.fetchAll()
        all2 = dao.fetchAll2()
        this.application = application
    }
    fun fetchAll2(): LiveData<List<Cashier>> {
        return all2
    }
    fun fetchAll(): LiveData<List<Cashier>> {
        return all
    }
    fun getcount(): Cursor? {
        return dao.getCount()
    }
        fun getOrder_noDetail(order_no:String): Cursor? {
        return dao.get_list(order_no)
    }
    fun getOrderNo(order_no:String): Cursor? {
        return dao.getorderNo(order_no)
    }
    fun getOrderLevel(order_no:String): Cursor? {
        return dao.getOrderStatus(order_no)
    }

    fun getOrderName(order_no:String): Cursor? {
        return dao.getOrderName(order_no)
    }

    fun getListOrders(order_no:String): Cursor? {
        return dao.getListOrders(order_no)
    }
    fun insertCashier(response: JSONArray) {
        Log.d("CashierData",response.toString())
        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                Log.d("CashierData  ", "START")
                dao.deleteAll()
                dao.deleteAllSequence()
                val items: ArrayList<Cashier> = ArrayList<Cashier>()
                for (i in 0 until response.length()) {
                    val data: JSONObject = response.getJSONObject(i)
                    val item = Cashier(0,
                        data.getString("order_no"),
                        data.getString("product_name"),
                        data.getString("product_quantity"),
                        data.getString("product_price"),
                        data.getString("add_ons"),
                        data.getString("sugar_percent"),
                        data.getString("total_price"),
                        data.getString("product_code"),
                        data.getString("order_status"),
                        data.getString("order_datetime"),
                        data.getString("scheduled_date"),
                        data.getString("name_order"),
                        data.getString("order_level"),

                    )
                    items.add(item)
                }
                dao.insertAll(items)
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {
                    Log.d("CashierData  ", "COMPLETE")
                }

                override fun onError(e: Throwable) {
                    Log.e("CashierData   ", e.message!!)
                }
            })
    }








}