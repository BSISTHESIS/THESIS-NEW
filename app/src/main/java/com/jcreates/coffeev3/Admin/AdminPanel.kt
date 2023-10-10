package com.jcreates.coffeev3.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import com.jcreates.coffeev3.ADMIN_PANEL.AdminCashier
import com.jcreates.coffeev3.Dashboard.Dashboard
import com.jcreates.coffeev3.R
import com.jcreates.coffeev3.ADMIN_PANEL.AdminCoffee
import com.jcreates.coffeev3.ADMIN_PANEL.AdminDrinks
import com.jcreates.coffeev3.ADMIN_PANEL.AdminFries
import com.jcreates.coffeev3.ADMIN_PANEL.AdminImage
import com.jcreates.coffeev3.ADMIN_PANEL.AdminNonCoffee
import com.jcreates.coffeev3.ADMIN_PANEL.AdminPopular
import com.jcreates.coffeev3.ADMIN_PANEL.AdminWaffle
import com.jcreates.coffeev3.DialogFragment.SalesPrintDialogFragment
import com.joey.kotlinandroidbeginning.API.RetroCallServer


class AdminPanel : AppCompatActivity() {
        lateinit var btn_popular : Button
        lateinit var btn_coffee :Button
        lateinit var btn_non_coffee: Button
        lateinit var btn_waffle:Button
        lateinit var btn_fries: Button
        lateinit var btn_drinks : Button
        lateinit var btn_images_slider : Button
        lateinit var btn_Cashier: Button
        lateinit var btn_sales : Button

    private var callServer: RetroCallServer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_panel)
        UiElement()
        Functions()
    }

    fun UiElement(){
        btn_popular = findViewById(R.id.button)
        btn_coffee =findViewById(R.id.button2)
        btn_non_coffee = findViewById(R.id.btn_non_coffee)
        btn_waffle = findViewById(R.id.btn_waffle)
        btn_fries = findViewById(R.id.btn_fries)
        btn_drinks = findViewById(R.id.btn_drinks)
        btn_images_slider = findViewById(R.id.btn_image_slider)
        btn_Cashier = findViewById(R.id.btn_cashier)
        btn_sales = findViewById(R.id.btn_sales)
    }
    fun Functions(){
        btn_popular.setOnClickListener {
            startActivity(Intent(applicationContext, AdminPopular::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        btn_coffee.setOnClickListener {
            startActivity(Intent(applicationContext, AdminCoffee::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        btn_non_coffee.setOnClickListener {
            startActivity(Intent(applicationContext, AdminNonCoffee::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        btn_waffle.setOnClickListener {
            startActivity(Intent(applicationContext, AdminWaffle::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        btn_fries.setOnClickListener {
            startActivity(Intent(applicationContext, AdminFries::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        btn_drinks.setOnClickListener {
            startActivity(Intent(applicationContext, AdminDrinks::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        btn_images_slider.setOnClickListener {
            startActivity(Intent(applicationContext, AdminImage::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        btn_Cashier.setOnClickListener {
            startActivity(Intent(applicationContext, AdminCashier::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        btn_sales.setOnClickListener {
            val args = Bundle()
            val userPopUp = SalesPrintDialogFragment()
            userPopUp.setArguments(args)
            supportFragmentManager
            userPopUp.show(supportFragmentManager, "my_dialog")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(applicationContext, Dashboard::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onStart() {
        super.onStart()
        callServer = RetroCallServer(this)
        runOnUiThread{
            callServer!!.RequestData("GET_POPULAR_LIST")
            callServer!!.RequestData("GET_COFFEE_LIST")
            callServer!!.RequestData("GET_NON_COFFEE_LIST")
            callServer!!.RequestData("GET_WAFFLE_LIST")
            callServer!!.RequestData("GET_FRIES_LIST")
            callServer!!.RequestData("GET_DRINKS_LIST")
            callServer!!.RequestData("GET_ADD_ONS")
            callServer!!.RequestData("GET_LIST_ORDER")
            callServer!!. RequestData("GET_SLIDE_IMAGE_LIST")
            callServer!!. RequestData("GET_LIST_CASHIER_ORDER")
        }

    }
}