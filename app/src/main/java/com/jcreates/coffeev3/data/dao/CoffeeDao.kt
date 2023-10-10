package com.jcreates.coffeev3.data.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jcreates.coffeev3.data.entity.Coffee


@Dao
interface CoffeeDao : BaseDao<Coffee> {
    @Query("SELECT * FROM tbl_coffee")
    fun fetchAll(): LiveData<List<Coffee>>

    @Query("SELECT * FROM tbl_coffee ")
    fun fetchAll2(): LiveData<List<Coffee>>

    @Query("SELECT * FROM tbl_coffee")
    fun fetch(): Coffee?

    @Query("DELETE from sqlite_sequence where name='tbl_coffee'")
    fun deleteAllSequence()

    @Query("DELETE FROM tbl_coffee")
    fun deleteAll()

    @Query("SELECT count(*)  FROM tbl_coffee  ")
    fun getCount(): Cursor?

    @Query("SELECT * FROM tbl_coffee  ")
    fun get_list(): Cursor?


}