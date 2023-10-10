package com.jcreates.coffeev3.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jcreates.coffeev3.data.dao.AddonsDao
import com.jcreates.coffeev3.data.dao.CashierDao
import com.jcreates.coffeev3.data.dao.CashierListOrderDao
import com.jcreates.coffeev3.data.dao.CoffeeDao
import com.jcreates.coffeev3.data.dao.CustomerDataDao
import com.jcreates.coffeev3.data.dao.CustomerDataOrderDao
import com.jcreates.coffeev3.data.dao.DrinksDao
import com.jcreates.coffeev3.data.dao.FriesDao
import com.jcreates.coffeev3.data.dao.ImagesDao
import com.jcreates.coffeev3.data.dao.Non_CoffeeDao
import com.jcreates.coffeev3.data.dao.PopularDao
import com.jcreates.coffeev3.data.dao.SalesDao
import com.jcreates.coffeev3.data.dao.SlideImageDao
import com.jcreates.coffeev3.data.dao.WaffleDao
import com.jcreates.coffeev3.data.entity.Addons
import com.jcreates.coffeev3.data.entity.Cashier
import com.jcreates.coffeev3.data.entity.CashierListOrder
import com.jcreates.coffeev3.data.entity.Coffee
import com.jcreates.coffeev3.data.entity.CustomerData
import com.jcreates.coffeev3.data.entity.CustomerDataOrder
import com.jcreates.coffeev3.data.entity.Drinks
import com.jcreates.coffeev3.data.entity.Fries
import com.jcreates.coffeev3.data.entity.Images
import com.jcreates.coffeev3.data.entity.Non_Coffee
import com.jcreates.coffeev3.data.entity.Popular
import com.jcreates.coffeev3.data.entity.SalesList
import com.jcreates.coffeev3.data.entity.SlideImage
import com.jcreates.coffeev3.data.entity.Waffle


@Database(entities = arrayOf(CustomerDataOrder::class,
    CustomerData::class, Addons::class, Popular::class, Coffee::class,
    Non_Coffee::class, Waffle::class, Fries::class, Drinks::class,SlideImage::class,Images::class,Cashier::class,
    CashierListOrder::class,SalesList::class), version = 1 , exportSchema = false)
abstract  class AppDatabase: RoomDatabase() {
    abstract  fun getPopularDao(): PopularDao
    abstract fun getCoffeeDao(): CoffeeDao
    abstract fun getNonCoffeeDao(): Non_CoffeeDao
    abstract fun getWaffleDao(): WaffleDao

    abstract fun getAddsOnDao(): AddonsDao
    abstract fun getFriesDao(): FriesDao
    abstract fun getDrinksDao(): DrinksDao

    abstract fun getImageSlideDao(): SlideImageDao

    abstract fun getCustomerDataDao(): CustomerDataDao
    abstract  fun getCustomerDataOrderDao(): CustomerDataOrderDao
    abstract  fun getImagesDao():ImagesDao

    abstract fun getCashierDao():CashierDao
    abstract fun getCashierListOrderDao():CashierListOrderDao

    abstract fun getSalesDao(): SalesDao
    companion object{

        @Volatile

        private var INSTANCE : AppDatabase?=null

        fun getDatabase(context: Context) : AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"house_cofffe").allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }

}