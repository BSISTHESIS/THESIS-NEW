package com.jcreates.houseofcoffee.DialogFragment

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.jcreates.coffeev3.R
import com.joey.kotlinandroidbeginning.API.RetroCallServer
import id.zelory.compressor.Compressor

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class InsertNewImagesDrinks : DialogFragment(){
    var imageView: ImageView? = null; var image_upload:android.widget.ImageView? = null
    private var callServer: RetroCallServer? = null
    private val context: Context? = null
    private val text_close: TextView? = null; private  var textView:TextView? = null
    private var saved: Button? = null
    var et_search: EditText? = null
    var et_header: EditText? = null; var et_price:EditText? = null
    private var exit: Button? = null
    private var header_text = ""; private  var base64:kotlin.String? = "";private var header_price = "";
    private var progressDialog: ProgressDialog? = null
//    private var currentUserRepository: CurrentUserRepository? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater: LayoutInflater = requireActivity().layoutInflater
        callServer = RetroCallServer(requireActivity())
        builder.setView(inflater.inflate(R.layout.insert_new_images_drinks, null))
        isCancelable = false

        return builder.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (progressDialog != null && progressDialog?.isShowing == true) {
            progressDialog?.cancel()
        }
    }

    fun UiElements(){
        progressDialog = ProgressDialog(getContext())
        saved = this.dialog!!.findViewById(R.id.btn_saved)
        exit = this.dialog!!.findViewById(R.id.btn_cancel)
        et_header = this.dialog!!.findViewById(R.id.et_header)
        image_upload = this.dialog!!.findViewById(R.id.upload_images)
        imageView = this.dialog!!.findViewById(R.id.image_view)
        et_price = this.dialog!!.findViewById(R.id.et_price)


        image_upload?.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 3)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val select_images = data.data
            imageView!!.setImageURI(select_images)
            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, select_images)
                // initialize byte stream
//                val stream = ByteArrayOutputStream()
//                // compress Bitmap
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//                // Initialize byte array
//                val bytes = stream.toByteArray()
//                // get base64 encoded string
//                base64 = Base64.encodeToString(bytes, Base64.DEFAULT)
//                // set encoded text on textview
//                Log.d("images", base64.toString())
                data?.data?.let { getRealPathFromURI(it,requireContext()) }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun getRealPathFromURI(uri: Uri, context: Context): String? {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)
        val nameIndex =  returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        val size = returnCursor.getLong(sizeIndex).toString()
        val file = File(context.filesDir, name)
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            var read = 0
            val maxBufferSize = 1 * 1024 * 1024
            val bytesAvailable: Int = inputStream?.available() ?: 0
            //int bufferSize = 1024;
            val bufferSize = Math.min(bytesAvailable, maxBufferSize)
            val buffers = ByteArray(bufferSize)
            while (inputStream?.read(buffers).also {
                    if (it != null) {
                        read = it
                    }
                } != -1) {
                outputStream.write(buffers, 0, read)
            }
            Log.e("File Size", "Size " + file.length())
            inputStream?.close()
            outputStream.close()
            Log.e("File Path", "Path " + file.path)

            try {
                val compressedImage = Compressor(context)
                    .setMaxWidth(500)
                    .setMaxHeight(500)
                    .setQuality(80)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(
                        Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES
                        ).absolutePath
                    )
                    .compressToBitmap(File(file.path))
                val stream = ByteArrayOutputStream()
                compressedImage.compress(Bitmap.CompressFormat.JPEG, 80, stream)
                val byteFormat = stream.toByteArray()
                base64 = Base64.encodeToString(
                    byteFormat,
                    Base64.DEFAULT
                )


                Toast.makeText(
                    context,
                    " Successfully, Added a Photo ",
                    Toast.LENGTH_LONG
                ).show()


            } catch (e: IOException) {
                e.printStackTrace()
            }

        } catch (e: java.lang.Exception) {
            Log.e("Exception", e.message!!)
        }
        return file.path
    }
    fun Components(){
        exit!!.setOnClickListener { dialog!!.dismiss() }


        saved!!.setOnClickListener {
            header_text = et_header!!.text.toString()
            header_price = et_price!!.text.toString()

            if (header_text.isEmpty()) {
                et_header!!.error = "PLEASE INPUT PRODUCT NAME "
                Toast.makeText(getContext(), "NO  PRODUCT NAME INPUT", Toast.LENGTH_LONG).show()
            } else if (base64!!.isEmpty()) {
                Toast.makeText(getContext(), "NO IMAGE UPLOADED", Toast.LENGTH_LONG).show()
            }else if (header_price.isEmpty()){
                et_price!!.error = "PLEASE INPUT  PRODUCT PRICE"
                Toast.makeText(getContext(), "NO  PRODUCT PRICE INPUT", Toast.LENGTH_LONG).show()
            }
            val sweetAlertDialog =
                SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
            sweetAlertDialog.titleText = "UPLOAD  THIS DRINKS PRODUCT?"
            sweetAlertDialog.confirmText = "CONFIRM"
            sweetAlertDialog.setCancelable(false)
            sweetAlertDialog.setConfirmClickListener {
                sweetAlertDialog.dismissWithAnimation()
                if (isNetworkAvailable()) {
                    progressDialog!!.show()
                    progressDialog!!.setCancelable(true)
                    progressDialog!!.setMessage("Please wait...")
                    Handler().postDelayed({ callServer?.SendDataDrinks(getdata2()) }, 2000)
                } else {
                    sweetAlertDialog.dismissWithAnimation()
                    val pDialog =
                        SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                    pDialog.titleText = "Oops..."
                    pDialog.confirmText = "Check"
                    pDialog.setCancelable(false)
                    pDialog.setContentText("No Internet Connection, Please Check your Internet Connection!")
                        .show()
                }
            }
            sweetAlertDialog.setCancelButton(
                "CANCEL"
            ) { sDialog ->
                sDialog.dismissWithAnimation()
                Toast.makeText(
                    requireContext(),
                    "Cancel Update For This Account!",
                    Toast.LENGTH_LONG
                ).show()
            }
            sweetAlertDialog.show()
        }

    }

    fun getdata2(): String {
//        currentUserRepository = activity?.application?.let { CurrentUserRepository(it) }
//        val cursor: Cursor? = currentUserRepository?.getUserInfo()
//        cursor?.moveToNext()
//        val employee_id = cursor?.getString(1)
        val header = JsonObject()
        var profile: JsonObject? = null
        val profile_holder = JsonArray()
        profile = JsonObject()
        profile.addProperty("header", header_text)
        profile.addProperty("base64", base64)
        profile.addProperty("product_price", header_price)
        profile_holder.add(profile)
        header.add("insert_drinks", profile_holder)
        Log.d("abc", header.toString())
        dismiss()
        return header.toString()
    }


    fun isNetworkAvailable(): Boolean {
    return try {
        val manager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkInfo: NetworkInfo? = null
        if (manager != null) {
            networkInfo = manager.activeNetworkInfo
        }
        networkInfo != null && networkInfo.isConnected
    } catch (e: NullPointerException) {
        false
    }
}


    override fun onStart() {
        super.onStart()
        UiElements()
        Components()
        val window = dialog!!.window ?: return
        val params = window.attributes
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        window.attributes = params
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


    }

    override fun onResume() {
        super.onResume()
        val window = dialog!!.window ?: return
        val params = window.attributes
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        window.attributes = params
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


}