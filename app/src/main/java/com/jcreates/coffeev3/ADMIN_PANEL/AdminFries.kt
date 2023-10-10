package com.jcreates.coffeev3.ADMIN_PANEL

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jcreates.coffeev3.Admin.AdminPanel
import com.jcreates.coffeev3.R


import com.jcreates.coffeev3.adapter.TaskAdapterShowFries
import com.jcreates.houseofcoffee.DialogFragment.InsertNewImagesFries
import com.joey.noteapplication.data.viewModel.FriesViewModel

class AdminFries : AppCompatActivity() {
    lateinit var btn_add : FloatingActionButton
    private var taskRecyclerView: RecyclerView? = null
    private var taskAdapter: TaskAdapterShowFries? = null
    private var friesViewModel: FriesViewModel? = null
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_fries)
        UiElement()
        Functions()
    }

    fun UiElement(){
        taskRecyclerView = findViewById(R.id.RecyclerNtr)
        taskRecyclerView?.setLayoutManager(
            LinearLayoutManager(
                this@AdminFries,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        taskRecyclerView?.setHasFixedSize(true)
        taskAdapter = TaskAdapterShowFries(this@AdminFries,this@AdminFries)
        taskRecyclerView?.setAdapter(taskAdapter)
        loadDataTask()
        btn_add = findViewById(R.id.add_fab)
    }

    private fun loadDataTask() {
        friesViewModel = ViewModelProvider(this).get(FriesViewModel::class.java)

        friesViewModel?.allnotes?.observe(this, Observer { user->
            taskAdapter?.setListData(user)
            taskAdapter?.notifyDataSetChanged()
        })
    }

    override fun onBackPressed() {

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



    fun Functions(){
        btn_add.setOnClickListener {
            val args = Bundle()
            val userPopUp = InsertNewImagesFries()
            userPopUp.setArguments(args)
            supportFragmentManager
            userPopUp.show(supportFragmentManager, "my_dialog")
        }
    }
}