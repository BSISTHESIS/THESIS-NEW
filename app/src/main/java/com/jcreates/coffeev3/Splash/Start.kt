package com.jcreates.coffeev3.Splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.jcreates.coffeev3.DialogFragment.InsertNameCustomer
import com.jcreates.coffeev3.R

class Start : AppCompatActivity() {
    lateinit var btn_start:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start)
        UiElement()
        Functions()
    }

    fun UiElement(){
        btn_start = findViewById(R.id.touch)
    }
    fun Functions(){
        btn_start.setOnClickListener {
//            startActivity(Intent(applicationContext, OrderStatus::class.java))
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            val args = Bundle()
            val userPopUp = InsertNameCustomer()
            userPopUp.setArguments(args)
            supportFragmentManager
            userPopUp.show(supportFragmentManager, "my_dialog")
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
}