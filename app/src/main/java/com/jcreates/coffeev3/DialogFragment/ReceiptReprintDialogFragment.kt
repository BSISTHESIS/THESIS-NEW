package com.jcreates.coffeev3.DialogFragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
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
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.jcreates.coffeev3.ADMIN_PANEL.AdminCashier
import com.jcreates.coffeev3.LoadingDialog
import com.jcreates.coffeev3.R
import com.jcreates.coffeev3.Splash.ImageSlide
import com.jcreates.coffeev3.data.repository.CashierRepository
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
import kotlinx.android.synthetic.main.card_order_list.total

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Locale

class ReceiptReprintDialogFragment : DialogFragment() {
    //    private static final String url_agri = WebService.getURL();
    private var cashierRepository: CashierRepository ? =null
    private var progressDialog: ProgressDialog? = null
    private var spinner_term: SmartMaterialSpinner<String>? = null
    lateinit var txtview_payment :TextView
    lateinit var et_payment_text :EditText
    lateinit var et_change :EditText
    lateinit var btn_compute: Button
    lateinit var btn_receipt: Button
    lateinit var btn_cancel: Button
    var c = Calendar.getInstance()
    var df = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US)
    val formattedDate = df.format(c.time)
    private var printing : Printing? = null
    var   final_change =""; var total_pay=""; var val_downpayment="";var total_dp=""; var val_monthly=""; var val_total_isv="";var val_final_amortization ="";
    private lateinit var et_term: EditText
    var final_dp : Double =0.0
    var order_no=""; var  payment = "" ; var money=""; var change="";
    private var callServer: RetroCallServer? = null
    var intent: Intent? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        builder.setView(inflater.inflate(R.layout.re_receipt_dialog, null))
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
        callServer = RetroCallServer(requireActivity())
        progressDialog = ProgressDialog(context)
        txtview_payment = dialog?.findViewById(R.id.finalpayment_txt)!!
        et_change = dialog?.findViewById(R.id.final_change_txt)!!
        et_payment_text = dialog?.findViewById(R.id.et_payment)!!
        btn_compute = dialog?.findViewById(R.id.btn_compute)!!
        btn_receipt = dialog?.findViewById(R.id.btn_print)!!
        btn_cancel = dialog?.findViewById(R.id.btn_cancel)!!

        cashierRepository = CashierRepository(requireActivity().application)

        if (Printooth.hasPairedPrinter())
            printing = Printooth.printer()
        initViews()
        initListeners()

    }

    private fun Listener() {
        val loading = LoadingDialog(requireActivity())
        intent = requireActivity().intent
        val bundle = arguments
        order_no = bundle!!.getString("order_no").toString()
        payment = bundle!!.getString("payment").toString()
        money = bundle!!.getString("money").toString()
        change = bundle!!.getString("final_change").toString()

        et_change.setText(change)
        et_payment_text.setText(money)
        txtview_payment.setText(payment)

        var cursor_view: Cursor? = cashierRepository?.getOrder_noDetail(order_no)
        cursor_view?.moveToNext()

        var final: Double? = cursor_view?.getString(0)?.toDouble()

        var formatters2 = DecimalFormat("#,###.00")
        var total_pay = formatters2.format(final)

        txtview_payment.setText(total_pay)


        btn_cancel!!.setOnClickListener {
            dialog!!.dismiss()
        }

        et_payment_text!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                val_downpayment = et_payment_text.text.toString()


                    onchangessDown(s.toString())


            }
        })

        btn_receipt.setOnClickListener {

            var change = et_change.text.toString()
            var payment = et_payment_text.toString()
            if(change.isEmpty()){
                et_change.setError("PLEASE INPUT PAYMENT")
                Toast.makeText(requireContext(),"PLEASE INPUT PAYMENT  ",Toast.LENGTH_LONG).show()
            }else if(payment.isEmpty()){
                et_payment_text.setError("PLEASE INPUT PAYMENT")
                android.widget.Toast.makeText(requireContext(),"PLEASE INPUT PAYMENT  ",
                    android.widget.Toast.LENGTH_LONG).show()
            }else{

                var dialog =  SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                dialog.setCancelable(false)
                dialog.setTitleText("COMPLETE  ORDER ")
                dialog.setContentText("ARE YOU SURE TO  RE-PRINT THIS OFFICIAL RECEIPT?")
                dialog .setConfirmText("YES")

                dialog.setConfirmClickListener {
                        sDialog -> sDialog.dismissWithAnimation()

                    loading.startLoading()
                    val handler = Handler()
                    handler.postDelayed(object :Runnable {
                        override fun run() {
                            printSomePrintable()
                            loading.isDismiss()
                            Toast.makeText(requireContext(),"PRINTING RE PRINTING OFFICIAL RECEIPT",Toast.LENGTH_LONG).show()
                            loading.startLoading()
//                            val handler = Handler()
//                            handler.postDelayed(object :Runnable {
//                                override fun run() {
//                                    callServer?.updateStatusOrder(order_no,total_pay,total_dp,final_change)
//
//                                }
//
//                            },2000)
                        }

                    },5000)
                }
                dialog.setCancelText("NO")
                dialog   .setCancelClickListener {
                        sDialog -> sDialog.dismissWithAnimation()
                    Toast.makeText(requireContext(),"CANCEL TO PROCEED PRINT OFFICIAL RECEIPT",Toast.LENGTH_LONG).show()
                }
                    .show()




            }
        }

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
                startActivity(Intent(requireContext(), AdminCashier::class.java))
                activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

                Toast.makeText(requireContext(), "Finish Print Official Receipt", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun getSomePrintables() = ArrayList<Printable>().apply {
        var cursor_view: Cursor? = cashierRepository?.getOrderNo(order_no)
        cursor_view?.moveToNext()


        var cursor_view2: Cursor? = cashierRepository?.getOrderLevel(order_no)
        cursor_view2?.moveToNext()


        var cursor_view3: Cursor? = cashierRepository?.getOrderName(order_no)
        cursor_view3?.moveToNext()

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
                .setText("OFFICIAL RECEIPT RE-PRINT")
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
                .setText("ORDER STATUS: "+cursor_view2?.getString(0)+"\n"+"ORDER NUMBER: "+cursor_view?.getString(0)+"\n"+"CUSTOMER NAME:  "+cursor_view3?.getString(0)+""+"\n")
                .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                .build())

//        add(
//            TextPrintable.Builder()
//                .setText("ORDER NUMBER: "+cursor_view?.getString(0)+"")
//                .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
//                .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
//                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
//                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
//                .build())
//
//
//        add(
//            TextPrintable.Builder()
//                .setText("CUSTOMER NAME:  "+cursor_view3?.getString(0)+"")
//                .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
//                .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
//                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
//                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
//                .setNewLinesAfter(1)
//                .build())
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



        val cursor_user: Cursor? =  cashierRepository?.getListOrders(order_no)
        while (cursor_user?.moveToNext()!!) {


            if(cursor_user?.getString(8).equals("code_coffee")){
                add(
                    TextPrintable.Builder()
                        .setText("PRODUCT NAME :"+cursor_user?.getString(2)+"")
                        .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                        .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                        .setNewLinesAfter(1)
                        .build())
                var final: Double? = cursor_user?.getString(7)?.toDouble()

                var formatters2 = DecimalFormat("#,###.00")
                var total_pay = formatters2.format(final)
                add(
                    TextPrintable.Builder()
                        .setText("Product Price: PHP "+cursor_user?.getString(4)+"\n"+"Quantity :"+cursor_user?.getString(3)+"\n"+"AddOns :"+cursor_user?.getString(5)+"\n"+"Total : PHP "+total_pay+"\n")
                        .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                        .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                        .setNewLinesAfter(2)
                        .build())

            }else{
                add(
                    TextPrintable.Builder()
                        .setText("PRODUCT NAME :"+cursor_user?.getString(2)+"")
                        .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                        .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                        .setNewLinesAfter(1)
                        .build())

                var final: Double? = cursor_user?.getString(7)?.toDouble()

                var formatters2 = DecimalFormat("#,###.00")
                var total_pay = formatters2.format(final)
                add(
                    TextPrintable.Builder()
                        .setText("Product Price: PHP "+cursor_user?.getString(4)+"\n"+"Quantity :"+cursor_user?.getString(3)+"\n"+"Total : PHP "+total_pay+"\n")
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

        var cursor_viewz: Cursor? = cashierRepository?.getOrder_noDetail(order_no)
        cursor_viewz?.moveToNext()

        var final: Double? = cursor_viewz?.getString(0)?.toDouble()

        var formatters2 = DecimalFormat("#,###.00")
         total_pay = formatters2.format(final)


        var payments = DecimalFormat("#,###.00")
        var total_pay = payments.format(payment.toDouble())
        add(
            TextPrintable.Builder()
                .setText("TOTAL PAYMENT: PHP "+total_pay)
                .setAlignment(DefaultPrinter.ALIGNMENT_RIGHT)
                .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                .setNewLinesAfter(2)
                .build())

        var formatters3 = DecimalFormat("#,###.00")
         total_dp = formatters3.format(final_dp)

        var money_txt = DecimalFormat("#,###.00")
        var moneys = money_txt.format(money.toDouble())

        add(
            TextPrintable.Builder()
                .setText("PAYMENT PHP "+moneys)
                .setAlignment(DefaultPrinter.ALIGNMENT_RIGHT)
                .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                .setNewLinesAfter(2)
                .build())


        var changet_txt = DecimalFormat("#,###.00")
        var changes = changet_txt.format(change.toDouble())
        add(
            TextPrintable.Builder()
                .setText("CHANGE: PHP "+changes.toString())
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
                .setText("THANK YOU FOR  ORDERING\n MAMAM COFFEE HOUSE ")
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