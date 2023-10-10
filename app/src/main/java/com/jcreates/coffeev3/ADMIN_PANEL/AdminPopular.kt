package com.jcreates.coffeev3.ADMIN_PANEL

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jcreates.coffeev3.Admin.AdminPanel
import com.jcreates.coffeev3.R



import com.jcreates.coffeev3.adapter.TaskAdapterShowPopular
import com.jcreates.houseofcoffee.DialogFragment.InsertNewImagesPopular
import com.joey.noteapplication.data.viewModel.PopularViewModel

class AdminPopular : AppCompatActivity() {
    lateinit var btn_add : FloatingActionButton
    private var taskRecyclerView: RecyclerView? = null
    private var taskAdapter: TaskAdapterShowPopular? = null
    private var adsImagesViewModel: PopularViewModel? = null
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_popular)
//        val actionBar = supportActionBar
//        actionBar!!.setBackgroundDrawable(
//            AppCompatResources.getDrawable(
//                applicationContext,
//                R.drawable.tool_gradient
//            )
//        )
//        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
//        supportActionBar!!.setCustomView(R.layout.popular_layout)
        UiElement()
        Functions()
    }

    fun UiElement(){
        taskRecyclerView = findViewById(R.id.RecyclerNtr)
        taskRecyclerView?.setLayoutManager(
            LinearLayoutManager(
                this@AdminPopular,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        taskRecyclerView?.setHasFixedSize(true)
        taskAdapter = TaskAdapterShowPopular(this@AdminPopular,this@AdminPopular)
        taskRecyclerView?.setAdapter(taskAdapter)
        loadDataTask()
        btn_add = findViewById(R.id.add_fab)
    }

    private fun loadDataTask() {
        adsImagesViewModel = ViewModelProvider(this).get(PopularViewModel::class.java)

        adsImagesViewModel?.allnotes?.observe(this, Observer { user->
            taskAdapter?.setListData(user)
            taskAdapter?.notifyDataSetChanged()
        })
    }

    override fun onBackPressed() {
//
//        startActivity(Intent(this, AdminPanel::class.java))
//        finish()
//        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
//            return
//        } else {
//            backToast =
//                Toast.makeText(applicationContext, "Press back again to exit", Toast.LENGTH_SHORT)
//            moveTaskToBack(true)
//            finish()
//        }
//        backPressedTime = System.currentTimeMillis()
        startActivity(Intent(applicationContext, AdminPanel::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
//
//

    fun Functions(){
        btn_add.setOnClickListener {
            val args = Bundle()
            val userPopUp = InsertNewImagesPopular()
            userPopUp.setArguments(args)
            supportFragmentManager
            userPopUp.show(supportFragmentManager, "my_dialog")
        }
    }
}