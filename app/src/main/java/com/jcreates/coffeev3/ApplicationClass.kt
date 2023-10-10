package com.jcreates.coffeev3

import android.app.Application
import com.jcreates.printooth.Printooth

class ApplicationClass : Application(){

    override fun onCreate() {
        super.onCreate()
        Printooth.init(this)
    }
}