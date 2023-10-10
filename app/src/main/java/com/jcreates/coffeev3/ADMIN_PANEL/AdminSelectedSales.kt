package com.jcreates.coffeev3.ADMIN_PANEL

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jcreates.coffeev3.Admin.AdminPanel
import com.jcreates.coffeev3.LoadingDialog
import com.jcreates.coffeev3.R
import com.jcreates.coffeev3.adapter.TaskAdapterShowOrderList
import com.jcreates.coffeev3.adapter.TaskAdapterShowSalesList
import com.jcreates.coffeev3.data.entity.CashierListOrder
import com.jcreates.coffeev3.data.repository.SalesRepository
import com.joey.kotlinandroidbeginning.API.RetroCallServer
import com.joey.noteapplication.data.viewModel.CashierListOrderViewModel
import com.joey.noteapplication.data.viewModel.SalesViewModel
import java.text.DecimalFormat
import java.util.Locale

class AdminSelectedSales : AppCompatActivity() {
    lateinit var btn_add : FloatingActionButton
    private var taskRecyclerView: RecyclerView? = null
    private var taskAdapter: TaskAdapterShowSalesList? = null
    private var adsImagesViewModel:SalesViewModel? = null
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null
    lateinit var salesRepository: SalesRepository
    lateinit var textView:TextView
    private var searchTask: EditText? = null
    private var taskActionButton: FloatingActionButton? = null
    private var callServer: RetroCallServer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_sales)
        UiElement()

    }

    fun UiElement(){
        callServer = RetroCallServer(this)
        taskRecyclerView = findViewById(R.id.RecyclerNtr)
        taskRecyclerView?.setLayoutManager(
            LinearLayoutManager(
                this@AdminSelectedSales,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        taskRecyclerView?.setHasFixedSize(true)
        taskAdapter = TaskAdapterShowSalesList(this@AdminSelectedSales,this@AdminSelectedSales,supportFragmentManager)
        taskRecyclerView?.setAdapter(taskAdapter)
        loadDataTask()
        val loading = LoadingDialog(this@AdminSelectedSales)
        searchTask = findViewById(R.id.searchTask)
        taskActionButton = findViewById(R.id.taskActionButton)
        salesRepository = SalesRepository(application)
        textView = findViewById(R.id.textView)
        var cursor = salesRepository.getGrandTotal()
        cursor?.moveToNext()
        var final: Double? = cursor?.getString(0)?.toDouble()

        var formatters2 = DecimalFormat("#,###.00")
        var total_pay = formatters2.format(final)
        textView.setText("GRAND TOTAL SALES :PHP "+total_pay)
        searchTask!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                searchDatabase(s.toString())
            }
        })

    }

    private fun loadDataTask() {
        adsImagesViewModel = ViewModelProvider(this).get(SalesViewModel::class.java)

        adsImagesViewModel?.allnotes?.observe(this, Observer { user->
            taskAdapter?.setListData(user)
            taskAdapter?.notifyDataSetChanged()
        })
    }




    private fun searchDatabase(query:String) {
        Log.d("dasd", query)
        val searchQuery = "%$query%"

        adsImagesViewModel?.searchDatabase(searchQuery)?.observe(this) { list ->
            list.let {
                taskAdapter?.setListData(it)

            }
        }

    }

    override fun onBackPressed() {

        super.onBackPressed()

        startActivity(Intent(applicationContext, AdminPanel::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }



}