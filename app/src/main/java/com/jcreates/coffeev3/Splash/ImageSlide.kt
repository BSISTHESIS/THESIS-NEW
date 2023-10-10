package com.jcreates.coffeev3.Splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jcreates.coffeev3.Dashboard.Dashboard
import com.jcreates.coffeev3.R
import com.jcreates.coffeev3.adapter.TaskAdapterShowImageSlide
import com.joey.kotlinandroidbeginning.API.RetroCallServer
import com.joey.noteapplication.data.viewModel.ImageViewModel
import java.util.Timer
import java.util.TimerTask

class ImageSlide : AppCompatActivity() {
    private var taskRecyclerView: RecyclerView? = null
    private var taskAdapter: TaskAdapterShowImageSlide? = null
    private var callServer: RetroCallServer? = null
    lateinit var linearLayout:LinearLayoutManager
    lateinit var requestListImages : ImageViewModel
    private var onRecentBackPressedTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_slide)



        elements()
        functions()
    }

    fun elements(){
        callServer = RetroCallServer(this@ImageSlide)
        requestListImages = ImageViewModel(application)
        linearLayout =   LinearLayoutManager(
           applicationContext,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        taskRecyclerView = findViewById(R.id.taskRecyclerView)
        taskRecyclerView?.setLayoutManager(linearLayout)


        taskRecyclerView?.setHasFixedSize(true)
        taskAdapter = TaskAdapterShowImageSlide(applicationContext,this@ImageSlide,supportFragmentManager)
        taskRecyclerView?.setAdapter(taskAdapter)

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(taskRecyclerView)
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val totalItemCount: Int = linearLayout.getItemCount()
                val lastVisible: Int = linearLayout.findLastVisibleItemPosition()
                val endHasBeenReached = lastVisible + 5 >= totalItemCount

                if (linearLayout.findLastCompletelyVisibleItemPosition()< (taskAdapter!!.itemCount-1)){
                    linearLayout.smoothScrollToPosition(taskRecyclerView,RecyclerView.State(),linearLayout.findLastCompletelyVisibleItemPosition()+1)
                }else if (linearLayout.findLastCompletelyVisibleItemPosition()< (taskAdapter!!.itemCount-1)){
                    linearLayout.smoothScrollToPosition(taskRecyclerView,
                        androidx.recyclerview.widget.RecyclerView.State(),0)
                }else       if (totalItemCount > 0 && endHasBeenReached) {
                    //you have reached to the bottom of your recycler view
                    linearLayout.smoothScrollToPosition(taskRecyclerView,RecyclerView.State(),0)
                }

            }
        }, 0, 3000)
        loadDataTask()
    }
    fun functions(){

        //none values
    }


    private fun loadDataTask() {
        requestListImages = ViewModelProvider(this).get(ImageViewModel::class.java)
        requestListImages?.allnotes?.observe(this, Observer { user->
            taskAdapter?.setListData(user)
            taskAdapter?.notifyDataSetChanged()
            val handler = Handler()
            handler.postDelayed(object :Runnable{
                override fun run() {

                }

            },2000)

        })
    }

    override fun onStart() {
        super.onStart()
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
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
        val n_act = Intent(application, Dashboard::class.java)
        startActivity(n_act)
    }

}