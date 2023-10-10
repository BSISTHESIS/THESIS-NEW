package com.jcreates.coffeev3.DialogFragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.jcreates.coffeev3.ADMIN_PANEL.AdminCashier
import com.jcreates.coffeev3.ADMIN_PANEL.AdminSelectedSales
import com.jcreates.coffeev3.Admin.AdminPanel
import com.jcreates.coffeev3.LoadingDialog
import com.jcreates.coffeev3.R
import com.jcreates.coffeev3.data.repository.CashierRepository
import com.jcreates.coffeev3.data.repository.SalesRepository
import com.jcreates.houseofcoffee.API.ApiParams
import com.jcreates.printooth.Printooth
import com.jcreates.printooth.data.printable.ImagePrintable
import com.jcreates.printooth.data.printable.Printable
import com.jcreates.printooth.data.printable.RawPrintable
import com.jcreates.printooth.data.printable.TextPrintable
import com.jcreates.printooth.data.printer.DefaultPrinter
import com.jcreates.printooth.ui.ScanningActivity
import com.jcreates.printooth.utilities.Printing
import com.jcreates.printooth.utilities.PrintingCallback
import com.joey.kotlinandroidbeginning.API.ApiInterface
import com.joey.kotlinandroidbeginning.API.ResponseModels
import com.joey.kotlinandroidbeginning.API.RetroCallServer
import com.joey.kotlinandroidbeginning.API.WebService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SalesPrintDialogFragment : DialogFragment() {
    //    private static final String url_agri = WebService.getURL();
    private var _apiInterface: ApiInterface? = null
    lateinit var salesRepository:SalesRepository
    val myCalendar = Calendar.getInstance()
    private var cashierRepository: CashierRepository ? =null
    private var progressDialog: ProgressDialog? = null
    private var spinner_term: SmartMaterialSpinner<String>? = null
    lateinit var txtview_payment :TextView
    lateinit var et_payment_text :EditText
    lateinit var et_date_end :EditText
    lateinit var et_change :EditText
    lateinit var btn_Show: Button
    lateinit var btn_receipt: Button
    lateinit var btn_cancel: Button
    var c = Calendar.getInstance()
    var df = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US)
    val formattedDate = df.format(c.time)
    private var printing : Printing? = null
    var   final_change =""; var total_pay=""; var val_downpayment="";var total_dp=""; var val_monthly=""; var val_total_isv="";var val_final_amortization ="";
    private lateinit var et_term: EditText
    var _defaultResponse: Observable<ResponseModels.DefaultResponse>? = null
    var final_dp : Double =0.0
    var order_no=""; var  payment = "" ; var money=""; var change="";var order_datetime=""; var order_status = "";var name_order = "";var test = "";
    private var callServer: RetroCallServer? = null
    var intent: Intent? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        builder.setView(inflater.inflate(R.layout.sales_receipt_dialog, null))
        isCancelable = false
        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.cancel()
        }
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onStart() {
        super.onStart()
        UiElements()
        Listener()
    }

    override fun onResume() {
        super.onResume()
        val window = dialog!!.window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private fun UiElements() {
        salesRepository = SalesRepository(requireActivity().application)
        callServer = RetroCallServer(requireActivity())
        progressDialog = ProgressDialog(context)
        txtview_payment = dialog?.findViewById(R.id.finalpayment_txt)!!
        et_change = dialog?.findViewById(R.id.final_change_txt)!!
        et_payment_text = dialog?.findViewById(R.id.et_payment)!!
        btn_Show = dialog?.findViewById(R.id.btn_show)!!
        btn_receipt = dialog?.findViewById(R.id.btn_print)!!
        btn_cancel = dialog?.findViewById(R.id.btn_cancel)!!
        _apiInterface = WebService.apiLink?.create(ApiInterface::class.java)
        cashierRepository = CashierRepository(requireActivity().application)
        et_date_end = dialog?.findViewById(R.id.et_date_end)!!
        if (Printooth.hasPairedPrinter())
            printing = Printooth.printer()
        initViews()
        initListeners()

    }

    @SuppressLint("SuspiciousIndentation")
    private fun Listener() {
        val loading = LoadingDialog(requireActivity())
        intent = requireActivity().intent
        val bundle = arguments
        order_no = bundle!!.getString("order_no").toString()
        payment = bundle!!.getString("payment").toString()
        money = bundle!!.getString("money").toString()
        change = bundle!!.getString("final_change").toString()

        et_change.setText(change)

        txtview_payment.setText(payment)

        var cursor_view: Cursor? = cashierRepository?.getOrder_noDetail(order_no)
        cursor_view?.moveToNext()



        btn_cancel!!.setOnClickListener {
            dialog!!.dismiss()
        }

        val date =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel()
            }

        et_payment_text.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        val date2 =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel2()
            }
        et_date_end.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                date2,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }



        btn_Show.setOnClickListener {

            var cursor_count = salesRepository.getcount()
            cursor_count?.moveToNext()
            Log.d("TEST- ", et_payment_text.text.toString());
            Log.d("TEST- ", et_date_end.text.toString());




//            if(cursor_count?.getString(0).equals("0")){
//                Toast.makeText(requireContext(),"NO LISTING AVAILABLE",Toast.LENGTH_LONG).show()
//
//
//            }else{

                var payment = et_payment_text.text.toString()
                var end_date = et_date_end.text.toString()
                if(payment.isEmpty()) {
                    et_payment_text.setError("PLEASE SELECT START DATE SALES")
                    android.widget.Toast.makeText(
                        requireContext(), "PLEASE SELECT START DATE SALES  ",
                        android.widget.Toast.LENGTH_LONG
                    ).show()
                } else    if(end_date.isEmpty()) {

                    et_date_end.setError("PLEASE SELECT END DATE SALES ")
                    android.widget.Toast.makeText(
                        requireContext(), "PLEASE SELECT END DATE SALES   ",
                        android.widget.Toast.LENGTH_LONG
                    ).show()


                }else{


                    forPrint2(et_payment_text.text.toString(), et_date_end.text.toString())
                    loading.startLoading()
                    val handler = Handler()
                    handler.postDelayed(object :Runnable {
                        override fun run() {
                            startActivity(Intent(requireContext(), AdminSelectedSales::class.java))
                            activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                            loading.isDismiss()

                        }

                    },3000)

                }
            


        }

//        et_payment_text!!.addTextChangedListener(object : TextWatcher {
//
//            override fun afterTextChanged(s: Editable) {}
//
//            override fun beforeTextChanged(s: CharSequence, start: Int,
//                                           count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int,
//                                       before: Int, count: Int) {
//                val_downpayment = et_payment_text.text.toString()
//
//
//                    onchangessDown(s.toString())
//
//
//            }
//        })

        btn_receipt.setOnClickListener {

//            var change = et_change.text.toString()
            var payment = et_payment_text.text.toString()
            var end_date = et_date_end.text.toString()
            Log.d("joey_test",et_payment_text.text.toString()+et_date_end.text.toString());
//            if(change.isEmpty()){
//                et_change.setError("PLEASE INPUT PAYMENT")
//                Toast.makeText(requireContext(),"PLEASE INPUT PAYMENT  ",Toast.LENGTH_LONG).show()
//            }else

                if(payment.isEmpty()) {
                    et_payment_text.setError("PLEASE SELECT START DATE SALES")
                    android.widget.Toast.makeText(
                        requireContext(), "PLEASE SELECT START DATE SALES  ",
                        android.widget.Toast.LENGTH_LONG
                    ).show()
                } else    if(end_date.isEmpty()) {

                    et_date_end.setError("PLEASE SELECT END DATE SALES ")
                    android.widget.Toast.makeText(
                        requireContext(), "PLEASE SELECT END DATE SALES   ",
                        android.widget.Toast.LENGTH_LONG
                    ).show()


            }else{

                var dialog =  SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                dialog.setCancelable(false)
                dialog.setTitleText("SALES REPORT ")
                dialog.setContentText("ARE YOU SURE TO  PRINT SALES REPORT FOR THE SELECTED DATE?")
                dialog .setConfirmText("YES")

                dialog.setConfirmClickListener {
                        sDialog -> sDialog.dismissWithAnimation()
                    forPrint(et_payment_text.text.toString(), et_date_end.text.toString())
                    loading.startLoading()
                    val handler = Handler()
                    handler.postDelayed(object :Runnable {
                        override fun run() {

                            Toast.makeText(requireContext(),"PRINTING SALES FROM SELECTED DATE",Toast.LENGTH_LONG).show()
                            loading.isDismiss()
                        }

                    },2000)
                }
                dialog.setCancelText("NO")
                dialog   .setCancelClickListener {
                        sDialog -> sDialog.dismissWithAnimation()
                    Toast.makeText(requireContext(),"CANCEL TO PROCEED PRINT SALES",Toast.LENGTH_LONG).show()
                }
                    .show()






            }
        }

    }
    fun updateLabel2() {
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        et_date_end.setText(dateFormat.format(myCalendar.time))


    }
     fun updateLabel() {
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        et_payment_text.setText(dateFormat.format(myCalendar.time))


    }

    private fun onchangessDown(query:String){

        if (!query.isEmpty()) {


            var cursor_view: Cursor? = cashierRepository?.getOrder_noDetail(order_no)
            cursor_view?.moveToNext()

            var final: Double? = cursor_view?.getString(0)?.toDouble()

//            var new_s = DecimalFormat("#,###.00")
//            var total_pay = new_s.format(final).toDouble()




             final_dp = val_downpayment?.toDouble()!!

            var amortization = final?.let { final_dp?.minus(it) }



            var final_amo: Double? = amortization?.toDouble()

            var formatters2 = DecimalFormat("#,###.00")
             final_change = formatters2.format(final_amo)


            et_change.setText(final_change)


        }
    }


    fun forPrint(payments:String,payments1:String){
        _defaultResponse =
            _apiInterface?.getData(ApiParams().getListAccountSales(payments,payments1))
        _defaultResponse?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    Log.d("test",response.getData().toString())
                    if (response.message.equals("SUCCESS")) {
                        salesRepository.insertSaleList(JSONArray(response.getData().toString()))
                        val handler = Handler()
                        handler.postDelayed(object :Runnable {
                            override fun run() {
                                printSomePrintable()
                            }

                        },3000)
                    } else {
                        progressDialog!!.dismiss()
                        Toast.makeText(
                            requireContext(),
                            " NO RECORD FOUND FOR SALES",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent: Intent = Intent(
                            requireContext(),
                            AdminPanel::class.java
                        )
                        startActivity(intent)
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("FOA", e.toString())
                }

                override fun onComplete() {

                }
            })
    }




    fun forPrint2(payments:String,payments1:String){
        _defaultResponse =
            _apiInterface?.getData(ApiParams().getListAccountSales(payments,payments1))
        _defaultResponse?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    Log.d("test",response.getData().toString())
                    if (response.message.equals("SUCCESS")) {
                        salesRepository.insertSaleList(JSONArray(response.getData().toString()))
                    } else {
                        progressDialog!!.dismiss()
                        Toast.makeText(
                            requireContext(),
                            " NO RECORD FOUND FOR SALES",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent: Intent = Intent(
                            requireContext(),
                            AdminPanel::class.java
                        )
                        startActivity(intent)
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("FOA", e.toString())
                }

                override fun onComplete() {

                }
            })
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
                Toast.makeText(requireContext(), "Connecting with printer", Toast.LENGTH_SHORT).show()
            }

            override fun printingOrderSentSuccessfully() {
                Toast.makeText(requireContext(), "Order sent to printer", Toast.LENGTH_SHORT).show()
            }

            override fun connectionFailed(error: String) {
                Toast.makeText(requireContext(), "Failed to connect printer", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: String) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }

            override fun onMessage(message: String) {
                Toast.makeText(requireContext(), "Message: $message", Toast.LENGTH_SHORT).show()
            }

            override fun disconnected() {
                startActivity(Intent(requireContext(), AdminPanel::class.java))
                activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

                Toast.makeText(requireContext(), "Finish Print Official Receipt", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun getSomePrintables() = ArrayList<Printable>().apply {

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

        val cursor_user: Cursor? =  salesRepository?.getListPopular()
        while (cursor_user?.moveToNext()!!) {

            add(
                TextPrintable.Builder()
                    .setText("SALES FOR DATE OF :" +cursor_user.getString(8))
                    .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                    .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                    .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                    .setNewLinesAfter(1)
                    .build()
            )




            add(
                TextPrintable.Builder()
                    .setText("CUSTOMER NAME::" +cursor_user.getString(7))
                    .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                    .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                    .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                    .setNewLinesAfter(1)
                    .build()
            )

            add(
                TextPrintable.Builder()
                    .setText("ORDER NO:" +cursor_user.getString(1))
                    .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                    .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                    .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                    .setNewLinesAfter(1)
                    .build()
            )


            add(
                TextPrintable.Builder()
                    .setText("ORDER DATE TIME:" +cursor_user.getString(2))
                    .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                    .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                    .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                    .setNewLinesAfter(1)
                    .build()
            )

            add(
                TextPrintable.Builder()
                    .setText("ORDER STATUS:" +cursor_user.getString(3))
                    .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                    .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                    .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                    .setNewLinesAfter(1)
                    .build()
            )
            var pays = DecimalFormat("#,###.00")
            var tt_pays = pays.format(cursor_user.getString(4).toDouble())
            add(
                TextPrintable.Builder()
                    .setText("PAYMENT: PHP " +tt_pays)
                    .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                    .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                    .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                    .setNewLinesAfter(1)
                    .build()
            )
            var moneys = DecimalFormat("#,###.00")
            var tt_moneys = moneys.format(cursor_user.getString(5).toDouble())
            add(
                TextPrintable.Builder()
                    .setText("MONEY: PHP " +tt_moneys)
                    .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                    .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                    .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                    .setNewLinesAfter(1)
                    .build()
            )

            var changes = DecimalFormat("#,###.00")
            var tt_changes = changes.format(cursor_user.getString(6).toDouble())
            add(
                TextPrintable.Builder()
                    .setText("CHANGE: PHP " +tt_changes)
                    .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                    .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                    .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                    .setNewLinesAfter(1)
                    .build()
            )
            add(
                TextPrintable.Builder()
                    .setText("-------------------------------\n -------------------------------")
                    .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                    .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                    .setNewLinesAfter(1)
                    .build())

        }

        add(
            TextPrintable.Builder()
                .setText("-------------------------------\n -------------------------------")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                .setNewLinesAfter(1)
                .build())

        val cursors: Cursor? =  salesRepository?.getGrandTotal()
        cursors?.moveToNext()


        var final: Double? = cursors?.getString(0)?.toDouble()

        var formatters2 = DecimalFormat("#,###.00")
        var total_pay = formatters2.format(final)



        add(
            TextPrintable.Builder()
                .setText("GRAND TOTAL SALES: PHP " +total_pay)
                .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                .setNewLinesAfter(1)
                .build()
        )

        add(
            TextPrintable.Builder()
                .setText("-------------------------------\n -------------------------------")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
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
                .setText("GENERATE SALES DATE AND TIME \n"+formattedDate)
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