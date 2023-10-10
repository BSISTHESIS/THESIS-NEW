package com.jcreates.coffeev3.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="tbl_cashier")
class Cashier(
    @PrimaryKey(autoGenerate = true) var id: Int =0,
    @ColumnInfo(name="order_no")val order_no:String,
    @ColumnInfo(name="product_name")val product_name:String,
    @ColumnInfo(name="quantity")val quantity:String,
    @ColumnInfo(name="product_price")val product_price:String,
    @ColumnInfo(name="add_ons") val add_ons:String,
    @ColumnInfo(name="sugar_percent") val sugar_percent:String,
    @ColumnInfo(name="total_price") val total_price:String,
    @ColumnInfo(name="product_code") val product_code:String,
    @ColumnInfo(name="order_status") val order_status:String,
    @ColumnInfo(name="order_datetime") val order_datetime:String,
    @ColumnInfo(name="schedule_date") val schedule_date:String,
    @ColumnInfo(name="name_order") val name_order:String,
    @ColumnInfo(name="order_level") val order_level:String,
)