package com.jcreates.coffeev3.data.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jcreates.coffeev3.data.entity.Images


@Dao
interface ImagesDao : BaseDao<Images> {
    @Query("SELECT * FROM tbl_images")
    fun fetchAll(): LiveData<List<Images>>
    @Query("SELECT * FROM tbl_images ")
    fun fetchAll2(): LiveData<List<Images>>

    @Query("SELECT * FROM tbl_images")
    fun fetch(): Images?

    @Query("DELETE from sqlite_sequence where name='tbl_images'")
    fun deleteAllSequence()

    @Query("DELETE FROM tbl_images")
    fun deleteAll()


    @Query("SELECT images FROM tbl_images  ")
    fun getImages(): Cursor?


}