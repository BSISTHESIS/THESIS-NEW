package com.jcreates.coffeev3.data.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jcreates.coffeev3.data.entity.Cashier
import com.jcreates.coffeev3.data.entity.Popular


@Dao
interface CashierDao : BaseDao<Cashier> {
    @Query("SELECT  * FROM tbl_cashier")
    fun fetchAll(): LiveData<List<Cashier>>

    @Query("SELECT * FROM tbl_cashier ")
    fun fetchAll2(): LiveData<List<Cashier>>

    @Query("SELECT * FROM tbl_cashier")
    fun fetch(): Cashier?

    @Query("DELETE from sqlite_sequence where name='tbl_cashier'")
    fun deleteAllSequence()

    @Query("DELETE FROM tbl_cashier")
    fun deleteAll()

    @Query("SELECT count(*)  FROM tbl_cashier  ")
    fun getCount(): Cursor?

    @Query("SELECT sum(round(total_price)) FROM tbl_cashier where order_no=:order_no  ")
    fun get_list(order_no:String): Cursor?


    @Query("SELECT order_no FROM tbl_cashier where order_no=:order_no LIMIT 1 ")
    fun getorderNo(order_no:String): Cursor?

    @Query("SELECT order_level FROM tbl_cashier where order_no=:order_no LIMIT 1 ")
    fun getOrderStatus(order_no:String): Cursor?


    @Query("SELECT name_order FROM tbl_cashier where order_no=:order_no LIMIT 1 ")
    fun getOrderName(order_no:String): Cursor?


    @Query("SELECT *  FROM tbl_cashier where order_no=:order_no  ")
    fun getListOrders(order_no:String): Cursor?
}