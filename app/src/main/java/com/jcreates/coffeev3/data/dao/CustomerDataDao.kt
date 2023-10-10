package com.jcreates.coffeev3.data.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jcreates.coffeev3.data.entity.CustomerData

@Dao
interface CustomerDataDao : BaseDao<CustomerData> {
    @Query("SELECT * FROM customer_tbl")
    fun fetchAll(): LiveData<List<CustomerData>>

    @Query("SELECT * FROM customer_tbl")
    fun fetch(): CustomerData?

    @Query("DELETE from sqlite_sequence where name='customer_tbl'")
    fun deleteAllSequence()

    @Query("DELETE FROM customer_tbl")
    fun deleteAll()

    @Query("SELECT count(*)  FROM customer_tbl  ")
    fun getLocation(): Cursor?

    @Query("UPDATE customer_tbl SET customer_order_request =:latitude")
    fun updateLoc(latitude: String?)

    @Query("SELECT * FROM customer_tbl  ")
    fun getLocations(): Cursor?

}