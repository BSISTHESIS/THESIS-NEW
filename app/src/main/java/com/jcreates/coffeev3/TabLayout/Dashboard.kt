package com.jcreates.coffeev3.TabLayout

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.jcreates.coffeev3.OrderSummary.OrderListSummary
import com.jcreates.coffeev3.R
import com.jcreates.coffeev3.Splash.ImageSlide
import com.jcreates.coffeev3.TabLayout.Fragment.FragmentCoffee
import com.jcreates.coffeev3.TabLayout.Fragment.FragmentDrinks
import com.jcreates.coffeev3.TabLayout.Fragment.FragmentFries
import com.jcreates.coffeev3.TabLayout.Fragment.FragmentNonCoffee
import com.jcreates.coffeev3.TabLayout.Fragment.FragmentPopular
import com.jcreates.coffeev3.TabLayout.Fragment.FragmentWaffle
import com.joey.noteapplication.data.viewModel.CustomerDataOrderViewModel

class Dashboard : AppCompatActivity() {
    lateinit var btn_floating : FloatingActionButton
    lateinit var handler: Handler
    lateinit var runnable: Runnable
    lateinit var count_cart:TextView
    private var onRecentBackPressedTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_dashboard)

        btn_floating = findViewById(R.id.add_fab)
        count_cart = findViewById(R.id.count_cart)
        var viewPager = findViewById(R.id.viewPager) as ViewPager
        var tablayout = findViewById(R.id.tablayout) as TabLayout

        val fragmentAdapter = FragmentAdapter(supportFragmentManager)
        fragmentAdapter.addFragment(FragmentPopular(),"POPULAR")
        fragmentAdapter.addFragment(FragmentCoffee(),"COFFEE")
        fragmentAdapter.addFragment(FragmentNonCoffee(),"NON COFFEE")
        fragmentAdapter.addFragment(FragmentWaffle(),"WAFFLE AND SANDWICHES")
        fragmentAdapter.addFragment(FragmentFries(),"FRIES")
//        fragmentAdapter.addFragment(FragmentDrinks(),"DRINKS")

        viewPager.adapter = fragmentAdapter
        tablayout.setupWithViewPager(viewPager)
        val  viewModel = ViewModelProvider(this).get(CustomerDataOrderViewModel::class.java)

        viewModel?.getTOTALSubmit()?.observe(this, Observer { user->
            count_cart.text = user.toString().replace("["," ").replace("]","")
            if(user.toString().equals("null")){
                btn_floating.isEnabled = false
            }
        })
        btn_floating.setOnClickListener {
                    startActivity(Intent(applicationContext, OrderListSummary::class.java))
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        }

        handler = Handler()
        runnable = Runnable { // TODO Auto-generated method stub
            Toast.makeText(this@Dashboard, "ORDER INACTIVE RE ORDER PLEASE TRY AGAIN TO ORDER", Toast.LENGTH_LONG).show()
            startActivity(Intent(applicationContext, ImageSlide::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        startHandler()

//        val viewModel: CustomerDataOrderViewModel = ViewModelProvider(this@Dashboard).get(CustomerDataOrderViewModel::class.java)
//
//
//        val observer2: Observer<Int> = object : Observer<Int> {
//            override fun onChanged(integer: Int) {
//                count_cart.setText(integer.toString() + "")
//            }
//        }
//
//        viewModel.allnotes.observe()


    }
    override fun onUserInteraction() {
        // TODO Auto-generated method stub
        super.onUserInteraction()
        stopHandler()
        startHandler()
    }
    private fun stopHandler() {
        handler.removeCallbacks(runnable)
    }
    private fun startHandler() {
        handler.postDelayed(runnable, 600000.toLong())
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