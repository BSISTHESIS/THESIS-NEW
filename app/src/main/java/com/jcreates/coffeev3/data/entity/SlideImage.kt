package com.jcreates.coffeev3.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="tbl_slide_image")
class SlideImage(
    @PrimaryKey(autoGenerate = true) var id: Int =0,
    @ColumnInfo(name="slide_id")val slide_id:String,
    @ColumnInfo(name="slide_image")val slide_image:String,
    @ColumnInfo(name="record_date")val record_date:String,
    @ColumnInfo(name="status")val status:String

)