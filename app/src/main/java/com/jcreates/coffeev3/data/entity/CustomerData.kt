package com.jcreates.coffeev3.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="customer_tbl")
class CustomerData(
    @PrimaryKey(autoGenerate = true) var id: Int =0,
    @ColumnInfo(name="customer_name")val customer_name:String,
    @ColumnInfo(name="customer_order_request") val customer_order_request:String

)