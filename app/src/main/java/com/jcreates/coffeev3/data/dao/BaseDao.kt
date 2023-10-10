package com.jcreates.coffeev3.data.dao

import androidx.room.*

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: Iterable<T>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: T): Long

    @Delete
    fun delete(entity: T)

    @Update
    fun update(entity: T)
}