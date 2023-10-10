package com.jcreates.coffeev3.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="tbl_waffle")
class Waffle(
    @PrimaryKey(autoGenerate = true) var id: Int =0,
    @ColumnInfo(name="product_id")val product_id:String,
    @ColumnInfo(name="product_name")val product_name:String,
    @ColumnInfo(name="product_price")val product_price:String,
    @ColumnInfo(name="product_image")val product_image:String,
    @ColumnInfo(name="product_record_datetime")val product_record_datetime:String,
    @ColumnInfo(name="product_status")val product_status:String,
    @ColumnInfo(name="product_code")val product_code:String
)