package com.jcreates.coffeev3.data.repository

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import com.jcreates.coffeev3.data.AppDatabase
import com.jcreates.coffeev3.data.dao.CustomerDataDao
import com.jcreates.coffeev3.data.entity.CustomerData

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class CustomerDataRepository(application: Application) {
    private val dao: CustomerDataDao
    private val all: LiveData<List<CustomerData>>
    private val application: Application

    init {
        val db: AppDatabase = AppDatabase.getDatabase(application)
        dao = db.getCustomerDataDao()
        all = dao.fetchAll()
        this.application = application
    }

    fun fetchAll(): LiveData<List<CustomerData>> {
        return all
    }
    fun getLocationCount(): Cursor? {
        return dao.getLocation()
    }
    fun getUserInfo(): Cursor? {
        return dao.getLocations()
    }


    fun insert(item: CustomerData?) {
        io.reactivex.rxjava3.core.Completable.fromAction {
            Log.d("CUSTOMER DATA", "START")
            dao.deleteAll()
            dao.deleteAllSequence()
            dao.insert(item!!)
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
            .subscribe(object : io.reactivex.rxjava3.core.CompletableObserver {
                override fun onSubscribe(d: io.reactivex.rxjava3.disposables.Disposable) {}
                override fun onComplete() {
                    Log.d("CUSTOMER DATA ", "COMPLETE")
                }

                override fun onError(e: Throwable) {
                    Log.e("CUSTOMER DATA ", e.message!!)
                }
            })
    }



    fun updateCustomerData(sample: String?) {
        io.reactivex.rxjava3.core.Completable.fromAction {
            Log.d("CustomerData", "START")
            dao.updateLoc(sample)
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
            .subscribe(object : io.reactivex.rxjava3.core.CompletableObserver {
                override fun onSubscribe(d: io.reactivex.rxjava3.disposables.Disposable) {}
                override fun onComplete() {
                    Log.d("CustomerData ", "COMPLETE")
                }

                override fun onError(e: Throwable) {
                    Log.e("CustomerData ", e.message!!)
                }
            })
    }




}