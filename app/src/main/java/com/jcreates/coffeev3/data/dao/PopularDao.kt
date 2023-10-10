package com.jcreates.coffeev3.data.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jcreates.coffeev3.data.entity.Popular


@Dao
interface PopularDao : BaseDao<Popular> {
    @Query("SELECT * FROM tbl_popular")
    fun fetchAll(): LiveData<List<Popular>>

    @Query("SELECT * FROM tbl_popular ")
    fun fetchAll2(): LiveData<List<Popular>>

    @Query("SELECT * FROM tbl_popular")
    fun fetch(): Popular?

    @Query("DELETE from sqlite_sequence where name='tbl_popular'")
    fun deleteAllSequence()

    @Query("DELETE FROM tbl_popular")
    fun deleteAll()

    @Query("SELECT count(*)  FROM tbl_popular  ")
    fun getCount(): Cursor?

    @Query("SELECT * FROM tbl_popular  ")
    fun get_list(): Cursor?


}