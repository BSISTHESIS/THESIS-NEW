package com.jcreates.coffeev3

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jcreates.printooth.Printooth
import com.jcreates.printooth.data.printable.ImagePrintable
import com.jcreates.printooth.data.printable.Printable
import com.jcreates.printooth.data.printable.RawPrintable
import com.jcreates.printooth.data.printable.TextPrintable
import com.jcreates.printooth.data.printer.DefaultPrinter
import com.jcreates.printooth.ui.ScanningActivity
import com.jcreates.printooth.utilities.Printing
import com.jcreates.printooth.utilities.PrintingCallback
import kotlinx.android.synthetic.main.activity_main.btnCustomPrinter
import kotlinx.android.synthetic.main.activity_main.btnPiarUnpair
import kotlinx.android.synthetic.main.activity_main.btnPrint
import kotlinx.android.synthetic.main.activity_main.btnPrintImages
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    var c = Calendar.getInstance()
    var df = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US)
    val formattedDate = df.format(c.time)
    private var printing : Printing? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Printooth.hasPairedPrinter())
            printing = Printooth.printer()
        initViews()
        initListeners()
    }

    private fun initViews() {
        btnPiarUnpair.text = if (Printooth.hasPairedPrinter()) "Un-pair ${Printooth.getPairedPrinter()?.name}" else "Pair with printer"
    }

    private fun initListeners() {
        btnPrint.setOnClickListener {
            if (!Printooth.hasPairedPrinter()) startActivityForResult(Intent(this,
                    ScanningActivity::class.java),
                    ScanningActivity.SCANNING_FOR_PRINTER)
            else printSomePrintable()
        }

        btnPrintImages.setOnClickListener {
            if (!Printooth.hasPairedPrinter()) startActivityForResult(Intent(this,
                    ScanningActivity::class.java),
                    ScanningActivity.SCANNING_FOR_PRINTER)
            else printSomeImages()
        }

        btnPiarUnpair.setOnClickListener {
            if (Printooth.hasPairedPrinter()) Printooth.removeCurrentPrinter()
            else startActivityForResult(Intent(this, ScanningActivity::class.java),
                    ScanningActivity.SCANNING_FOR_PRINTER)
            initViews()
        }

        btnCustomPrinter.setOnClickListener {
            startActivity(Intent(this, WoosimActivity::class.java))
        }

        printing?.printingCallback = object : PrintingCallback {
            override fun connectingWithPrinter() {
                Toast.makeText(this@MainActivity, "Connecting with printer", Toast.LENGTH_SHORT).show()
            }

            override fun printingOrderSentSuccessfully() {
                Toast.makeText(this@MainActivity, "Order sent to printer", Toast.LENGTH_SHORT).show()
            }

            override fun connectionFailed(error: String) {
                Toast.makeText(this@MainActivity, "Failed to connect printer", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: String) {
                Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
            }

            override fun onMessage(message: String) {
                Toast.makeText(this@MainActivity, "Message: $message", Toast.LENGTH_SHORT).show()
            }

            override fun disconnected() {
                Toast.makeText(this@MainActivity, "Disconnected Printer", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun printSomePrintable() {
        val printables = getSomePrintables()
        printing?.print(printables)
    }

    private fun printSomeImages() {
        val printables = ArrayList<Printable>().apply {
            add(ImagePrintable.Builder(R.drawable.image1, resources).build())
            add(ImagePrintable.Builder(R.drawable.image2, resources).build())
            add(ImagePrintable.Builder(R.drawable.image3, resources).build())
        }
        printing?.print(printables)
    }

    private fun getSomePrintables() = ArrayList<Printable>().apply {
        add(RawPrintable.Builder(byteArrayOf(27, 100, 4)).build()) // feed lines example in raw mode

        add(ImagePrintable.Builder(R.drawable.test, resources)
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .build())
        add(TextPrintable.Builder()
            .setText("-------------------------------")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
            .setNewLinesAfter(1)
            .build())
        add(TextPrintable.Builder()
            .setText("Dafer Building, \n Sto Cristo, Guagua Pampanga")
            .setLineSpacing(DefaultPrinter.LINE_SPACING_60)

            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
            .setNewLinesAfter(1)
            .build())
        add(TextPrintable.Builder()
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

        add(TextPrintable.Builder()
            .setText("ORDER STATUS: DINE IN\n")
            .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
            .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
            .build())


        add(TextPrintable.Builder()
            .setText("CUSTOMER NAME: \n IS-60")
            .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
            .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
            .setNewLinesAfter(1)
            .build())
        add(TextPrintable.Builder()
            .setText("-------------------------------\n -------------------------------")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
            .setNewLinesAfter(1)
            .build())
        add(TextPrintable.Builder()
            .setText("CART ORDER  LIST :")
            .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
            .setNewLinesAfter(1)
            .build())



        add(TextPrintable.Builder()
            .setText("-------------------------------\n -------------------------------")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
            .setNewLinesAfter(2)
            .build())

        add(TextPrintable.Builder()
            .setText("THANK YOU FOR YOUR ORDERING, \n PLEASE WAIT TO CALL YOUR NAME!")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
            .setNewLinesAfter(2)
            .build())

        add(TextPrintable.Builder()
            .setText("ORDER DATE AND TIME \n"+formattedDate)
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
            .setNewLinesAfter(2)
            .build())


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ScanningActivity.SCANNING_FOR_PRINTER && resultCode == Activity.RESULT_OK)
            printSomePrintable()
        initViews()
    }
}
