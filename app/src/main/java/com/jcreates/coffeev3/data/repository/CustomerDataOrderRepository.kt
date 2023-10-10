package com.jcreates.coffeev3.data.repository

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData

import com.jcreates.coffeev3.data.dao.CustomerDataOrderDao
import com.jcreates.coffeev3.data.entity.CustomerDataOrder
import com.jcreates.coffeev3.data.AppDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class CustomerDataOrderRepository(application: Application) {
    private val dao: CustomerDataOrderDao
    private val all: LiveData<List<CustomerDataOrder>>

    private val application: Application

    init {
        val db: AppDatabase = AppDatabase.getDatabase(application)
        dao = db.getCustomerDataOrderDao()
        all = dao.fetchAll()
        this.application = application
    }

    fun fetchAll(): LiveData<List<CustomerDataOrder>> {
        return all
    }



    fun totalCart(): LiveData<List<Integer>> {
        return dao.fetchAllcount()
    }
    fun totalPrice(): LiveData<List<Integer>> {
        return dao.fetchAllprice()
    }
    fun getLocationCount(): Cursor? {
        return dao.getLocation()
    }
    fun getUserInfo(): Cursor? {
        return dao.getLocations()
    }


    fun getTotalPayment(): Cursor? {
        return dao.getTotalPayment()
    }

    fun delteOrder(ids:String){
        dao.deleteAllOrder(ids)
    }

    fun  delete(){
        dao.deleteAllSequence()
        dao.deleteAll()
    }

    fun insert(item: CustomerDataOrder?) {
        io.reactivex.rxjava3.core.Completable.fromAction {
            Log.d("CustomerDataOrder ", "START")
            dao.insert(item!!)
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
            .subscribe(object : io.reactivex.rxjava3.core.CompletableObserver {
                override fun onSubscribe(d: io.reactivex.rxjava3.disposables.Disposable) {}
                override fun onComplete() {
                    Log.d("CustomerDataOrder  ", "COMPLETE")
                }

                override fun onError(e: Throwable) {
                    Log.e(" CustomerDataOrder ", e.message!!)
                }
            })
    }



//    fun updateCustomerData(latitude: String?) {
//        io.reactivex.rxjava3.core.Completable.fromAction {
//            Log.d("CustomerData", "START")
//            dao.updateLoc(latitude)
//        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
//            .subscribe(object : io.reactivex.rxjava3.core.CompletableObserver {
//                override fun onSubscribe(d: io.reactivex.rxjava3.disposables.Disposable) {}
//                override fun onComplete() {
//                    Log.d("CustomerData ", "COMPLETE")
//                }
//
//                override fun onError(e: Throwable) {
//                    Log.e("CustomerData ", e.message!!)
//                }
//            })
//    }




}