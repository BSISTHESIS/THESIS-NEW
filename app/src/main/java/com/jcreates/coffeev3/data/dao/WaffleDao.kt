package com.jcreates.coffeev3.data.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jcreates.coffeev3.data.entity.Waffle


@Dao
interface WaffleDao : BaseDao<Waffle> {
    @Query("SELECT * FROM tbl_waffle")
    fun fetchAll(): LiveData<List<Waffle>>
    @Query("SELECT * FROM tbl_waffle ")
    fun fetchAll2(): LiveData<List<Waffle>>

    @Query("SELECT * FROM tbl_waffle")
    fun fetch(): Waffle?

    @Query("DELETE from sqlite_sequence where name='tbl_waffle'")
    fun deleteAllSequence()

    @Query("DELETE FROM tbl_waffle")
    fun deleteAll()

    @Query("SELECT count(*)  FROM tbl_waffle  ")
    fun getCount(): Cursor?

    @Query("SELECT * FROM tbl_waffle  ")
    fun get_list(): Cursor?


}