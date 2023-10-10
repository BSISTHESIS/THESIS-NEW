package com.jcreates.coffeev3.data.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jcreates.coffeev3.data.entity.CustomerDataOrder

@Dao
interface CustomerDataOrderDao : BaseDao<CustomerDataOrder> {
    @Query("SELECT * FROM customer_order_tbl")
    fun fetchAll(): LiveData<List<CustomerDataOrder>>

    @Query("SELECT count(*) FROM customer_order_tbl")
    fun fetchAllcount(): LiveData<List<Integer>>

    @Query("SELECT sum(total_price) FROM customer_order_tbl")
    fun fetchAllprice(): LiveData<List<Integer>>

    @Query("SELECT * FROM customer_order_tbl")
    fun fetch(): CustomerDataOrder?

    @Query("DELETE from sqlite_sequence where name='customer_order_tbl'")
    fun deleteAllSequence()

    @Query("DELETE FROM customer_order_tbl")
    fun deleteAll()


    @Query("DELETE FROM customer_order_tbl where id=:ids")
    fun deleteAllOrder(ids:String)

    @Query("SELECT count(*)  FROM customer_order_tbl  ")
    fun getLocation(): Cursor?

//    @Query("UPDATE customer_order_tbl SET customer_order_request =:latitude")
//    fun updateLoc(latitude: String?)

    @Query("SELECT * FROM customer_order_tbl  ")
    fun getLocations(): Cursor?

    @Query("SELECT sum(round(total_price)) FROM customer_order_tbl  ")
    fun getTotalPayment(): Cursor?


}