package com.jcreates.coffeev3

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.UpdateAvailability
import com.jcreates.coffeev3.Dashboard.Dashboard
import com.jcreates.coffeev3.Splash.ImageSlide
import kotlinx.android.synthetic.main.activity_admin_panel.button
import kotlinx.android.synthetic.main.activity_login2.etLoginPass

class LoginActivity : AppCompatActivity() {
    lateinit var editext : EditText
    lateinit var btn_start :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        editext = findViewById(R.id.etLoginPass)
      //variable
        btn_start = findViewById(R.id.btnLogin)


        btn_start.setOnClickListener {
            if(editext.text.toString().equals("001122")){
                startActivity(Intent(applicationContext, Dashboard::class.java))
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }else{
                etLoginPass.requestFocus()
                etLoginPass.setText("")
                Toast.makeText(this,"INVALID PASSWORD TO LOGIN",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        UpdateApp()
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