package com.jcreates.coffeev3.data.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jcreates.coffeev3.data.entity.Drinks


@Dao
interface DrinksDao : BaseDao<Drinks> {
    @Query("SELECT * FROM tbl_drinks")
    fun fetchAll(): LiveData<List<Drinks>>
    @Query("SELECT * FROM tbl_drinks ")
    fun fetchAll2(): LiveData<List<Drinks>>

    @Query("SELECT * FROM tbl_drinks")
    fun fetch(): Drinks?

    @Query("DELETE from sqlite_sequence where name='tbl_drinks'")
    fun deleteAllSequence()

    @Query("DELETE FROM tbl_drinks")
    fun deleteAll()

    @Query("SELECT count(*)  FROM tbl_drinks  ")
    fun getCount(): Cursor?

    @Query("SELECT * FROM tbl_drinks  ")
    fun get_list(): Cursor?


}