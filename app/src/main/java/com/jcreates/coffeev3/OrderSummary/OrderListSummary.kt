package com.jcreates.coffeev3.OrderSummary

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.jcreates.coffeev3.LoadingDialog
import com.jcreates.coffeev3.R
import com.jcreates.coffeev3.Splash.ImageSlide
import com.jcreates.coffeev3.TabLayout.Dashboard
import com.jcreates.coffeev3.adapter.TaskAdapterOrderList
import com.jcreates.coffeev3.data.repository.CustomerDataOrderRepository
import com.jcreates.coffeev3.data.repository.CustomerDataRepository
import com.jcreates.printooth.Printooth
import com.jcreates.printooth.data.printable.ImagePrintable
import com.jcreates.printooth.data.printable.Printable
import com.jcreates.printooth.data.printable.RawPrintable
import com.jcreates.printooth.data.printable.TextPrintable
import com.jcreates.printooth.data.printer.DefaultPrinter
import com.jcreates.printooth.ui.ScanningActivity
import com.jcreates.printooth.utilities.Printing
import com.jcreates.printooth.utilities.PrintingCallback
import com.joey.kotlinandroidbeginning.API.RetroCallServer
import com.joey.noteapplication.data.viewModel.CustomerDataOrderViewModel
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class OrderListSummary : AppCompatActivity() {
    var c = Calendar.getInstance()
    var df = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US)
    val formattedDate = df.format(c.time)
    private var printing : Printing? = null
    lateinit var btn_back :ImageView
    lateinit var txt_total : TextView
    lateinit var btn_procceed: Button
    private var taskRecyclerView: RecyclerView? = null
    private var taskAdapter: TaskAdapterOrderList? = null
    private var customerDataOrderViewModel: CustomerDataOrderViewModel? = null
    private var customerDataOrderRepository : CustomerDataOrderRepository ?= null
    private var customerDataRepository : CustomerDataRepository ? =null
    private var callServer: RetroCallServer? = null
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_list_summary)
        val loading = LoadingDialog(this@OrderListSummary)
        callServer = RetroCallServer(this@OrderListSummary)
        customerDataOrderRepository = CustomerDataOrderRepository(application)
        customerDataRepository = CustomerDataRepository(application)
            UiElements()
            functions()
        if (Printooth.hasPairedPrinter())
            printing = Printooth.printer()
        initViews()
        initListeners()
    }
    fun UiElements(){
        btn_procceed = findViewById(R.id.btn_addcard_)
        btn_back = findViewById(R.id.back_btn)
        txt_total = findViewById(R.id.value_)
        taskRecyclerView = findViewById(R.id.RecyclerNtr)
        taskRecyclerView?.setLayoutManager(
            LinearLayoutManager(
                this@OrderListSummary,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        taskRecyclerView?.setHasFixedSize(true)
        taskAdapter = TaskAdapterOrderList(this@OrderListSummary,this@OrderListSummary)
        taskRecyclerView?.setAdapter(taskAdapter)
        loadDataTask()

        val  viewModel = ViewModelProvider(this).get(CustomerDataOrderViewModel::class.java)

        viewModel?.getTotalPrice()?.observe(this, Observer { user->
            if(user.toString().equals("null")){
                txt_total.text = "GRAND TOTAL : PHP 00.00"
            }else{
                txt_total.text = "GRAND TOTAL : PHP "+user.toString().replace("["," ").replace("]","")+".00"
            }

        })
    }
    private fun loadDataTask() {
        customerDataOrderViewModel = ViewModelProvider(this).get(CustomerDataOrderViewModel::class.java)

        customerDataOrderViewModel?.allnotes?.observe(this, Observer { user->
            taskAdapter?.setListData(user)
            taskAdapter?.notifyDataSetChanged()
        })
    }
    fun functions(){
        val loading = LoadingDialog(this@OrderListSummary)
        var callServer = RetroCallServer(this@OrderListSummary)
        btn_back.setOnClickListener {
            startActivity(Intent(applicationContext, Dashboard::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        btn_procceed.setOnClickListener {
            if (!Printooth.hasPairedPrinter()) startActivityForResult(Intent(this,
                ScanningActivity::class.java),
                ScanningActivity.SCANNING_FOR_PRINTER)
            else{

               var dialog =  SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    dialog.setCancelable(false)
                dialog.setTitleText("SUMMARY ORDER CHECK ")
                dialog.setContentText("ARE YOU SURE TO YOUR ALL ORDER?")
                dialog .setConfirmText("YES")

                dialog.setConfirmClickListener {
                            sDialog -> sDialog.dismissWithAnimation()
                        callServer?.SendDataOrder(getdata2())
                        loading.startLoading()
                        val handler = Handler()
                        handler.postDelayed(object :Runnable {
                            override fun run() {
                                printSomePrintable()
                                loading.isDismiss()
                                Toast.makeText(applicationContext,"PRINTING YOUR ORDER",Toast.LENGTH_LONG).show()
                            }

                        },5000)
                    }
                dialog.setCancelText("NO")
                dialog   .setCancelClickListener {
                            sDialog -> sDialog.dismissWithAnimation()
                        Toast.makeText(applicationContext,"CANCEL TO PROCEED PRINT ORDER",Toast.LENGTH_LONG).show()
                    }
                    .show()


            }
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


    private fun initViews() {
      //  btnPiarUnpair.text = if (Printooth.hasPairedPrinter()) "Un-pair ${Printooth.getPairedPrinter()?.name}" else "Pair with printer"
    }

    private fun printSomePrintable() {
        val printables = getSomePrintables()
        printing?.print(printables)
    }

    private fun initListeners() {



        printing?.printingCallback = object : PrintingCallback {
            override fun connectingWithPrinter() {
                Toast.makeText(this@OrderListSummary, "Connecting with printer", Toast.LENGTH_SHORT).show()
            }

            override fun printingOrderSentSuccessfully() {
                Toast.makeText(this@OrderListSummary, "Order sent to printer", Toast.LENGTH_SHORT).show()
            }

            override fun connectionFailed(error: String) {
                Toast.makeText(this@OrderListSummary, "Failed to connect printer", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: String) {
                Toast.makeText(this@OrderListSummary, error, Toast.LENGTH_SHORT).show()
            }

            override fun onMessage(message: String) {
                Toast.makeText(this@OrderListSummary, "Message: $message", Toast.LENGTH_SHORT).show()
            }

            override fun disconnected() {
                startActivity(Intent(applicationContext, ImageSlide::class.java))
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)


                val intent: Intent = Intent(this@OrderListSummary, ImageSlide::class.java)
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                )
                startActivity(intent)


                Toast.makeText(this@OrderListSummary, "Finish Print", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun getdata2(): String {
        var cursor_customertbl : Cursor? = customerDataRepository?.getUserInfo()
        cursor_customertbl?.moveToNext()

        val header = JsonObject()
        var profile: JsonObject? = null
        val profile_holder = JsonArray()

        val cursor_user: Cursor? = customerDataOrderRepository?.getUserInfo()
        while (cursor_user?.moveToNext()!!) {
            profile = JsonObject()
            profile.addProperty("product_name", cursor_user?.getString(1).toString())
            profile.addProperty("product_quantity", cursor_user?.getString(2).toString())
            profile.addProperty("product_price", cursor_user?.getString(3).toString())
            profile.addProperty("adds_ons", cursor_user?.getString(4).toString())
            profile.addProperty("sugar_percent", cursor_user?.getString(5).toString())
            profile.addProperty("total_price", cursor_user?.getString(6).toString())
            profile.addProperty("product_code", cursor_user?.getString(7).toString())
            profile.addProperty("order_status", "PENDING")
            profile.addProperty("name_order", cursor_customertbl?.getString(1))
            profile.addProperty("order_level", cursor_customertbl?.getString(2))


            profile_holder.add(profile)
        }
        header.add("update_data", profile_holder)
        Log.d("abc", header.toString())


        return header.toString()
    }



    private fun getSomePrintables() = ArrayList<Printable>().apply {
        var cursor_view: Cursor? = customerDataOrderRepository?.getUserInfo()
        cursor_view?.moveToNext()

        var cursor_totalPayment : Cursor? = customerDataOrderRepository?.getTotalPayment()
        cursor_totalPayment?.moveToNext()

        var cursor_customertbl : Cursor? = customerDataRepository?.getUserInfo()
        cursor_customertbl?.moveToNext()

        add(RawPrintable.Builder(byteArrayOf(27, 100, 4)).build()) // feed lines example in raw mode

        add(
            ImagePrintable.Builder(R.drawable.test, resources)
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .build())
        add(
            TextPrintable.Builder()
            .setText("-------------------------------")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
            .setNewLinesAfter(1)
            .build())
        add(
            TextPrintable.Builder()
            .setText("Dafer Building, \n Sto Cristo, Guagua Pampanga")
            .setLineSpacing(DefaultPrinter.LINE_SPACING_60)

            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
            .setNewLinesAfter(1)
            .build())
        add(
            TextPrintable.Builder()
            .setText("-------------------------------\n -------------------------------")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
            .setNewLinesAfter(1)
            .build())




        add(
            TextPrintable.Builder()
                .setText("ORDER FORM")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                .setNewLinesAfter(1)
                .build())


        add(
            TextPrintable.Builder()
                .setText("-------------------------------\n -------------------------------")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                .setNewLinesAfter(1)
                .build())


//
//        add(TextPrintable.Builder()
//                .setText("Hello World : été è à €")
//                .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
//                .setNewLinesAfter(1)
//                .build())

        add(
            TextPrintable.Builder()
                .setText("ORDER STATUS: "+cursor_customertbl?.getString(2)+"\n"+"CUSTOMER NAME: "+cursor_customertbl?.getString(1)+"\n")
                .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                .build())
//        add(
//            TextPrintable.Builder()
//            .setText("CUSTOMER NAME: "+cursor_customertbl?.getString(1)+"")
//            .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
//            .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
//            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
//            .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
//            .setNewLinesAfter(1)
//            .build())
        add(
            TextPrintable.Builder()
            .setText("-------------------------------\n -------------------------------")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
            .setNewLinesAfter(1)
            .build())
        add(
            TextPrintable.Builder()
            .setText("CART ORDER  LIST :")
            .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
            .setNewLinesAfter(1)
            .build())



        val cursor_user: Cursor? = customerDataOrderRepository?.getUserInfo()
        while (cursor_user?.moveToNext()!!) {


            if(cursor_user?.getString(7).equals("code_coffee")){
                add(
                    TextPrintable.Builder()
                        .setText("PRODUCT NAME :"+cursor_user?.getString(1)+"")
                        .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                        .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                        .setNewLinesAfter(1)
                        .build())
                var final: Double? = cursor_user?.getString(6)?.toDouble()

                var formatters2 = DecimalFormat("#,###.00")
                var total_pay = formatters2.format(final)
                add(
                    TextPrintable.Builder()
                        .setText("Product Price: PHP "+cursor_user?.getString(3)+"\n"+"Quantity :"+cursor_user?.getString(2)+"\n"+"AddOns :"+cursor_user?.getString(4)+"\n"+"Sugar Percent :"+cursor_user?.getString(5)+"\n"+"Total : PHP "+total_pay+"\n")
                        .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                        .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                        .setNewLinesAfter(2)
                        .build())

                }else{
                    add(
                        TextPrintable.Builder()
                            .setText("PRODUCT NAME :"+cursor_user?.getString(1)+"")
                            .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                            .setNewLinesAfter(1)
                            .build())

                var final: Double? = cursor_user?.getString(6)?.toDouble()

                var formatters2 = DecimalFormat("#,###.00")
                var total_pay = formatters2.format(final)
                    add(
                        TextPrintable.Builder()
                            .setText("Product Price: PHP "+cursor_user?.getString(3)+"\n"+"Quantity :"+cursor_user?.getString(2)+"\n"+"Total : PHP "+total_pay+"\n")
                            .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                            .setNewLinesAfter(2)
                            .build())
                }


        }


        add(
            TextPrintable.Builder()
                .setText("-------------------------------\n -------------------------------")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                .setNewLinesAfter(2)
                .build())

        var final: Double? = cursor_totalPayment?.getString(0)?.toDouble()

        var formatters2 = DecimalFormat("#,###.00")
        var total_pay = formatters2.format(final)
        add(
            TextPrintable.Builder()
                .setText("TOTAL PAYMENT: PHP "+total_pay)
                .setAlignment(DefaultPrinter.ALIGNMENT_RIGHT)
                .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                .setNewLinesAfter(2)
                .build())


        add(
            TextPrintable.Builder()
            .setText("-------------------------------\n -------------------------------")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
            .setNewLinesAfter(2)
            .build())

        add(
            TextPrintable.Builder()
            .setText("THANK YOU FOR  ORDERING, \n PLEASE WAIT TO CALL YOUR NAME!")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
            .setNewLinesAfter(2)
            .build())

        add(
            TextPrintable.Builder()
            .setText("ORDER DATE AND TIME \n"+formattedDate)
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
            .setNewLinesAfter(3)
            .build())


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ScanningActivity.SCANNING_FOR_PRINTER && resultCode == Activity.RESULT_OK)
            printSomePrintable()
        initViews()
    }

}