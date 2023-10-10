package com.jcreates.coffeev3.data.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jcreates.coffeev3.data.entity.Non_Coffee


@Dao
interface Non_CoffeeDao : BaseDao<Non_Coffee> {
    @Query("SELECT * FROM tbl_non_coffee")
    fun fetchAll(): LiveData<List<Non_Coffee>>

    @Query("SELECT * FROM tbl_non_coffee ")
    fun fetchAll2(): LiveData<List<Non_Coffee>>

    @Query("SELECT * FROM tbl_non_coffee")
    fun fetch(): Non_Coffee?

    @Query("DELETE from sqlite_sequence where name='tbl_non_coffee'")
    fun deleteAllSequence()

    @Query("DELETE FROM tbl_non_coffee")
    fun deleteAll()

    @Query("SELECT count(*)  FROM tbl_non_coffee  ")
    fun getCount(): Cursor?

    @Query("SELECT * FROM tbl_non_coffee  ")
    fun get_list(): Cursor?


}