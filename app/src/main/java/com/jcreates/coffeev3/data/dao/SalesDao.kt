package com.jcreates.coffeev3.data.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jcreates.coffeev3.data.entity.CashierListOrder
import com.jcreates.coffeev3.data.entity.SalesList
import kotlinx.coroutines.flow.Flow


@Dao
interface SalesDao : BaseDao<SalesList> {
    @Query("SELECT  *  FROM tbl_sales where payment IS NOT NULL order by order_status desc")
    fun fetchAll(): LiveData<List<SalesList>>

    @Query("SELECT * FROM tbl_sales ")
    fun fetchAll2(): LiveData<List<SalesList>>

    @Query("SELECT * FROM tbl_sales")
    fun fetch(): SalesList?

    @Query("DELETE from sqlite_sequence where name='tbl_sales'")
    fun deleteAllSequence()

    @Query("DELETE FROM tbl_sales")
    fun deleteAll()

    @Query("SELECT count(*)  FROM tbl_sales  ")
    fun getCount(): Cursor?

    @Query("SELECT * FROM tbl_sales  ")
    fun get_list(): Cursor?

    @Query("SELECT sum(payment) FROM tbl_sales where payment IS NOT NULL")
    fun getGrandTotal(): Cursor?

    @Query("SELECT * FROM tbl_sales WHERE order_no LIKE :client_name_ OR name_order LIKE :client_name_  ")
    fun searchDatabase(client_name_:String): Flow<List<SalesList>>


}