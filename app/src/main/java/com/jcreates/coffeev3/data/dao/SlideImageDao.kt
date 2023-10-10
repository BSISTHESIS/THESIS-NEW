package com.jcreates.coffeev3.data.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jcreates.coffeev3.data.entity.SlideImage


@Dao
interface SlideImageDao : BaseDao<SlideImage> {
    @Query("SELECT * FROM tbl_slide_image where status='ACTIVE'")
    fun fetchAll(): LiveData<List<SlideImage>>

    @Query("SELECT * FROM tbl_slide_image ")
    fun fetchAll2(): LiveData<List<SlideImage>>

    @Query("SELECT * FROM tbl_slide_image")
    fun fetch(): SlideImage?

    @Query("DELETE from sqlite_sequence where name='tbl_slide_image'")
    fun deleteAllSequence()

    @Query("DELETE FROM tbl_slide_image")
    fun deleteAll()

    @Query("SELECT count(*)  FROM tbl_slide_image  ")
    fun getCount(): Cursor?

    @Query("SELECT * FROM tbl_slide_image  ")
    fun get_list(): Cursor?


}