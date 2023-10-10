package com.jcreates.coffeev3.data.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jcreates.coffeev3.data.entity.Addons


@Dao
interface AddonsDao : BaseDao<Addons> {
    @Query("SELECT * FROM tbl_add_ons")
    fun fetchAll(): LiveData<List<Addons>>
    @Query("SELECT * FROM tbl_add_ons where status='ACTIVE'")
    fun fetchAll2(): LiveData<List<Addons>>

    @Query("SELECT * FROM tbl_add_ons")
    fun fetch(): Addons?

    @Query("DELETE from sqlite_sequence where name='tbl_add_ons'")
    fun deleteAllSequence()

    @Query("DELETE FROM tbl_add_ons")
    fun deleteAll()

    @Query("SELECT count(*)  FROM tbl_add_ons  ")
    fun getCount(): Cursor?

    @Query("SELECT * FROM tbl_add_ons  where status='ACTIVE'")
    fun get_list(): Cursor?

    @Query("SELECT * FROM tbl_add_ons  where add_ons_name=:name and status='ACTIVE'")
    fun get_lists(name:String): Cursor?

    @Query("SELECT count(1) FROM tbl_add_ons  where add_ons_name=:name and status='ACTIVE'")
    fun get_lists_count(name:String): Cursor?


}