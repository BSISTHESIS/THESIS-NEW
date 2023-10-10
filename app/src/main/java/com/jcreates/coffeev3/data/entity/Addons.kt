package com.jcreates.coffeev3.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="tbl_add_ons")
class Addons(
    @PrimaryKey(autoGenerate = true) var id: Int =0,
    @ColumnInfo(name="ids")val ids:String,
    @ColumnInfo(name="add_ons_name")val add_ons_name:String,
    @ColumnInfo(name="add_ons_price")val add_ons_price:String,
    @ColumnInfo(name="record_date")val record_date:String,
    @ColumnInfo(name="status")val status:String

)