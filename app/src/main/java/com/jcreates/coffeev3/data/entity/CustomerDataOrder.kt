package com.jcreates.coffeev3.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="customer_order_tbl")
class CustomerDataOrder(
    @PrimaryKey(autoGenerate = true) var id: Int =0,
    @ColumnInfo(name="product_name")val product_name:String,
    @ColumnInfo(name="quantity")val quantity:String,
    @ColumnInfo(name="product_price")val product_price:String,
    @ColumnInfo(name="add_ons") val add_ons:String,
    @ColumnInfo(name="sugar_percent") val sugar_percent:String,
    @ColumnInfo(name="total_price") val total_price:String,
    @ColumnInfo(name="product_code") val product_code:String

    )