package com.jcreates.coffeev3.ORDERSTATUS

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jcreates.coffeev3.R
import com.jcreates.coffeev3.Splash.ImageSlide
import com.jcreates.coffeev3.Splash.Start
import com.joey.noteapplication.data.viewModel.CustomerDataViewModel

class OrderStatus : AppCompatActivity() {
    lateinit var btn_dine : Button
    lateinit var btn_take : Button
    lateinit var  customerDataViewModel: CustomerDataViewModel
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null
    private var onRecentBackPressedTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_status)

        UiElements()
        Functions()

    }
    fun UiElements(){
        btn_dine = findViewById(R.id.button)
        btn_take = findViewById(R.id.button2)
        customerDataViewModel = CustomerDataViewModel(application)
    }
    fun Functions(){
        btn_dine.setOnClickListener {
            startActivity(Intent(applicationContext, com.jcreates.coffeev3.TabLayout.Dashboard::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            customerDataViewModel.update("DINE-IN")
        }
        btn_take.setOnClickListener {
            startActivity(Intent(applicationContext, com.jcreates.coffeev3.TabLayout.Dashboard::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            customerDataViewModel.update("TAKE-OUT")
        }
    }

    override fun onStart() {
        super.onStart()
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - onRecentBackPressedTime > 2000) {
            onRecentBackPressedTime = System.currentTimeMillis()
            Snackbar.make(
                findViewById(android.R.id.content),
                " PLEASE PRESS BACK AGAIN TO GO START..AGAIN AND CANCEL ALL ORDER..",
                Snackbar.LENGTH_LONG
            ).show()
            return
        }
        super.onBackPressed()
        val n_act = Intent(application, ImageSlide::class.java)
        startActivity(n_act)
    }
}