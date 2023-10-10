package com.jcreates.coffeev3.Dashboard

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.UpdateAvailability
import com.jcreates.coffeev3.Admin.AdminPanel
import com.jcreates.coffeev3.MainActivity
import com.jcreates.coffeev3.R
import com.jcreates.coffeev3.Splash.ImageSlide
import com.joey.kotlinandroidbeginning.API.RetroCallServer
import okhttp3.internal.concurrent.Task


class Dashboard : AppCompatActivity() {
    lateinit var btn_admin : Button
    lateinit var btn_menu : Button
    lateinit var btn_blue: Button
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null
    private var onRecentBackPressedTime: Long = 0
    private var callServer: RetroCallServer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(com.jcreates.coffeev3.R.layout.activity_dashboard)
    elements()
    functions()
    }

    fun elements(){
        callServer = RetroCallServer(this@Dashboard)
        btn_admin = findViewById(R.id.btn_admin)
        btn_menu = findViewById(R.id.btn_dashboard)
        btn_blue = findViewById(R.id.btn_blue)
    }
    fun functions(){
        btn_admin.setOnClickListener {
            startActivity(Intent(applicationContext, AdminPanel::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        btn_menu.setOnClickListener {
            startActivity(Intent(applicationContext, ImageSlide::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        btn_blue.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

    }


    override fun onStart() {
        super.onStart()
        UpdateApp();
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
            callServer!!.RequestData("GET_SLIDE_IMAGE_LIST")
            callServer!!.RequestData("GET_LIST_CASHIER_ORDER")
        }

    }


    fun UpdateApp() {
        val appUpdateManager: AppUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.getAppUpdateInfo()
        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { result ->
            if (result.updateAvailability() === UpdateAvailability.UPDATE_AVAILABLE) {
//                requestUpdate(result);
                val ctw: ContextThemeWrapper =
                    ContextThemeWrapper(this, android.R.style.Theme)
                val alertDialogBuilder =
                    AlertDialog.Builder(ctw)
                alertDialogBuilder.setTitle("Update HOUSE OF COFFE ")
                alertDialogBuilder.setCancelable(false)

                alertDialogBuilder.setMessage(" HOUSE OF COFFE recommends that you update to the latest version for a seamless & enhanced performance of the app.")
                alertDialogBuilder.setPositiveButton(
                    "Update"
                ) { dialog, id ->
                    try {
                        startActivity(
                            Intent(
                                "android.intent.action.VIEW",
                                Uri.parse("market://details?id=$packageName")
                            )
                        )
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                "android.intent.action.VIEW",
                                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                            )
                        )
                    }
                }
                alertDialogBuilder.show()
            } else {
            }
        }
    }


}