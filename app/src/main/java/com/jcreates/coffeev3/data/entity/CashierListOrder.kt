package com.jcreates.coffeev3.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="tbl_cashier_list_order")
class CashierListOrder(
    @PrimaryKey(autoGenerate = true) var id: Int =0,
    @ColumnInfo(name="order_no")val order_no:String,
    @ColumnInfo(name="name_order") val name_order:String,
    @ColumnInfo(name="order_level") val order_level:String,
    @ColumnInfo(name="order_status") val order_status:String,
    @ColumnInfo(name="payment") val payment:String,
    @ColumnInfo(name="money") val money:String,
    @ColumnInfo(name="final_change") val final_change:String
)