package com.jcreates.coffeev3.ADMIN_PANEL

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jcreates.coffeev3.R
import com.jcreates.houseofcoffee.DialogFragment.InsertNewImagesWaffle

import com.jcreates.coffeev3.adapter.TaskAdapterShowWaffle
import com.joey.noteapplication.data.viewModel.WaffleViewModel

class AdminWaffle : AppCompatActivity() {
    lateinit var btn_add : FloatingActionButton
    private var taskRecyclerView: RecyclerView? = null
    private var taskAdapter: TaskAdapterShowWaffle? = null
    private var waffleViewModel: WaffleViewModel? = null
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_waffle)
        UiElement()
        Functions()
    }

    fun UiElement(){
        taskRecyclerView = findViewById(R.id.RecyclerNtr)
        taskRecyclerView?.setLayoutManager(
            LinearLayoutManager(
                this@AdminWaffle,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        taskRecyclerView?.setHasFixedSize(true)
        taskAdapter = TaskAdapterShowWaffle(this@AdminWaffle,this@AdminWaffle)
        taskRecyclerView?.setAdapter(taskAdapter)
        loadDataTask()
        btn_add = findViewById(R.id.add_fab)
    }

    private fun loadDataTask() {
        waffleViewModel = ViewModelProvider(this).get(WaffleViewModel::class.java)

        waffleViewModel?.allnotes?.observe(this, Observer { user->
            taskAdapter?.setListData(user)
            taskAdapter?.notifyDataSetChanged()
        })
    }

//    override fun onBackPressed() {
//
//        startActivity(Intent(this, AdminPanel::class.java))
//        finish()
//        if (backPressedTime + 2000 > System.currentTimeMillis()) {
//            super.onBackPressed()
//            return
//        } else {
//            backToast =
//                Toast.makeText(applicationContext, "Press back again to exit", Toast.LENGTH_SHORT)
//            moveTaskToBack(true)
//            finish()
//        }
//        backPressedTime = System.currentTimeMillis()
//    }



    fun Functions(){
        btn_add.setOnClickListener {
            val args = Bundle()
            val userPopUp = InsertNewImagesWaffle()
            userPopUp.setArguments(args)
            supportFragmentManager
            userPopUp.show(supportFragmentManager, "my_dialog")
        }
    }
}