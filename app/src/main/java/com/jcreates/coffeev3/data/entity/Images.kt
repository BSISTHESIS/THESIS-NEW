package com.jcreates.coffeev3.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="tbl_images")
class Images(
    @PrimaryKey(autoGenerate = true) var id: Int =0,
    @ColumnInfo(name="images")val ids:String,

)