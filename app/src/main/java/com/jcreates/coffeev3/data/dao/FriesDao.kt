package com.jcreates.coffeev3.data.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jcreates.coffeev3.data.entity.Fries


@Dao
interface FriesDao : BaseDao<Fries> {
    @Query("SELECT * FROM tbl_fries")
    fun fetchAll(): LiveData<List<Fries>>
    @Query("SELECT * FROM tbl_fries ")
    fun fetchAll2(): LiveData<List<Fries>>

    @Query("SELECT * FROM tbl_fries")
    fun fetch(): Fries?

    @Query("DELETE from sqlite_sequence where name='tbl_fries'")
    fun deleteAllSequence()

    @Query("DELETE FROM tbl_fries")
    fun deleteAll()

    @Query("SELECT count(*)  FROM tbl_fries  ")
    fun getCount(): Cursor?

    @Query("SELECT * FROM tbl_fries  ")
    fun get_list(): Cursor?


}