package com.jcreates.coffeev3.data.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jcreates.coffeev3.data.entity.Cashier
import com.jcreates.coffeev3.data.entity.CashierListOrder
import com.jcreates.coffeev3.data.entity.Popular
import kotlinx.coroutines.flow.Flow


@Dao
interface CashierListOrderDao : BaseDao<CashierListOrder> {
    @Query("SELECT  *  FROM tbl_cashier_list_order order by order_status desc")
    fun fetchAll(): LiveData<List<CashierListOrder>>

    @Query("SELECT * FROM tbl_cashier_list_order ")
    fun fetchAll2(): LiveData<List<CashierListOrder>>

    @Query("SELECT * FROM tbl_cashier_list_order")
    fun fetch(): CashierListOrder?

    @Query("DELETE from sqlite_sequence where name='tbl_cashier_list_order'")
    fun deleteAllSequence()

    @Query("DELETE FROM tbl_cashier_list_order")
    fun deleteAll()

    @Query("SELECT count(*)  FROM tbl_cashier_list_order  ")
    fun getCount(): Cursor?

    @Query("SELECT * FROM tbl_cashier_list_order  ")
    fun get_list(): Cursor?
    @Query("SELECT * FROM tbl_cashier_list_order WHERE order_no LIKE :client_name_ OR name_order LIKE :client_name_  ")
    fun searchDatabase(client_name_:String): Flow<List<CashierListOrder>>

}