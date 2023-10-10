package com.jcreates.coffeev3.ORDERSTATUS


import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.jcreates.coffeev3.R
import com.jcreates.coffeev3.data.entity.CustomerDataOrder
import com.jcreates.coffeev3.data.repository.AddOnsRepository
import com.jcreates.coffeev3.data.repository.PopularRepository
import com.joey.noteapplication.data.viewModel.CustomerDataOrderViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.math.roundToInt


class OrderItem2 : AppCompatActivity() {
     lateinit var btn_back :ImageView
     lateinit var btn_add : Button
      lateinit var text_product : TextView
      lateinit var text_price : TextView
    lateinit var image_order : CircleImageView
      lateinit var checkbox_nata : CheckBox
    lateinit var checkbox_oreo : CheckBox
    lateinit var checkbox_syrup : CheckBox
    lateinit var checkbox_poppingboba : CheckBox
    lateinit var checkbox_whippedcream : CheckBox
    lateinit var checkbox_espesso : CheckBox
    lateinit var textView1: TextView
    lateinit var textView2: TextView
    lateinit var textView3: TextView
    lateinit var textView4: TextView
    lateinit var textView5: TextView
    lateinit var textView6: TextView
    lateinit var radioGroup:RadioGroup
    lateinit var btn_decrease: Button
    var minteger = 0
    var value_quantity :String = ""
     var value_radio :String = ""
    private lateinit var addOnsRepository:AddOnsRepository
    private lateinit var popularRepository: PopularRepository
    var totalamount = 0
    var price = 0; var price1 = 0; var price2 = 0; var price3 = 0; var price4 = 0; var price5 = 0;
    var product_name = ""
    lateinit var customerDataOrderViewModel: CustomerDataOrderViewModel
    var bitmap: Bitmap? = null

    var result = ""
     lateinit var cartTotal: TextView
     lateinit var addButton: Button
     lateinit var subtractButton: Button

     lateinit var  relativelayout_add : RelativeLayout
     lateinit var  relative_sugar_percent:RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_item2)
        Ui()
        functions()
        setOnCheckedChangeListener()


    }

    fun Ui(){
        customerDataOrderViewModel = CustomerDataOrderViewModel(application)
        addOnsRepository = AddOnsRepository(this.application)
        popularRepository = PopularRepository(this.application)
        btn_back = findViewById(R.id.back_btn)
        radioGroup = findViewById(R.id.radioGroup)
        text_price = findViewById(R.id.product_price)
        text_product = findViewById(R.id.product_name)
        image_order = findViewById(R.id.image_order)
        checkbox_nata = findViewById(R.id.checkbox_nata)
        checkbox_oreo = findViewById(R.id.checkbox_oreo)
        checkbox_syrup = findViewById(R.id.checkbox_syrup)
        checkbox_whippedcream = findViewById(R.id.checkbox_whippedcream)
        checkbox_poppingboba = findViewById(R.id.checkbox_poppingboba)
        checkbox_espesso = findViewById(R.id.checkbox_espesso)
        textView1 = findViewById(R.id.price_add1)
        textView2 = findViewById(R.id.price_add2)
        textView3 = findViewById(R.id.price_add3)
        textView4 = findViewById(R.id.price_add4)
        textView5 = findViewById(R.id.price_add5)
        textView6 = findViewById(R.id.price_add6)
        btn_add = findViewById(R.id.btn_addcard_)
        addButton = findViewById(R.id.btn_add_item)
        subtractButton = findViewById(R.id.btn_minus_item)
        cartTotal = findViewById(R.id.value_)
        relativelayout_add = findViewById(R.id.list_)
        relative_sugar_percent = findViewById(R.id.list_2)
        val intent = getIntent();
        val myValue = intent.getStringExtra("product_image")
        val myProduct = intent.getStringExtra("product_name")
        var myProducprice = intent.getStringExtra("product_price")

        text_price.setText(myProducprice)
        text_product.setText(myProduct)

        runOnUiThread {
            //        imageView.setImageBitmap(decodedByte);

//            val imageBytes = Base64.decode(myValue.toString(), Base64.DEFAULT)
//            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//
//
//            Glide.with(applicationContext).asBitmap().load(decodedImage).optionalFitCenter()
//                .into(image_order)

            val decodedString: ByteArray =
                Base64.decode(myValue.toString(), Base64.DEFAULT)
            val pixel1 = 1000
            val pixel2 = 1000
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            this.bitmap = Bitmap.createScaledBitmap(decodedByte, pixel1, pixel2, true)

            image_order.setImageBitmap(bitmap)
        }

        addButton.setOnClickListener {
            val currentTotal = cartTotal.text.toString().toInt()
            cartTotal.text = (currentTotal + 1).toString()
        }

        subtractButton.setOnClickListener {
            val currentTotal = cartTotal.text.toString().toInt()
            if (currentTotal > 0) {
                cartTotal.text = (currentTotal - 1).toString()
            }
        }

    }
    fun increaseInteger(view: View?) {
        minteger = minteger + 1
        display(minteger)

    }

    fun decreaseInteger(view: View?) {
        minteger = minteger - 1
        display(minteger)

    }

    private fun display(number: Int) {
        val displayInteger = findViewById<View>(
            com.jcreates.coffeev3.R.id.value_
        ) as TextView
        displayInteger.text = "" + number
        value_quantity = number.toInt().toString()

    }
    private fun setOnCheckedChangeListener() {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val text =
                if (R.id.radio1 == checkedId) {
                    value_radio = "0 %";
                }else if (R.id.radio2 == checkedId){
                    value_radio = "10 %";
                } else if (R.id.radio3 == checkedId){
                    value_radio = "25 %";
                }else if (R.id.radio4== checkedId){
                    value_radio = "30 %";
                }else if (R.id.radio5 == checkedId){
                    value_radio = "50 %";
                }else if (R.id.radio6 == checkedId){
                    value_radio = "75 %";
                }else if (R.id.radio7 == checkedId){
                    value_radio = "100 %";
                }else {
                    value_radio = "0 %";
                }

            Toast.makeText(applicationContext, value_radio, Toast.LENGTH_SHORT).show()
        }
    }

    fun functions(){


        var myProducprice = intent.getStringExtra("product_price").toString().toDouble()
        totalamount  = totalamount+myProducprice.toInt()
        btn_back.setOnClickListener {
            onBackPressed()
        }
        addOnsRepository = AddOnsRepository(this.application)

        val cusor_count_nata = addOnsRepository.getListAddCount("NATA")
        cusor_count_nata?.moveToNext()

        if(cusor_count_nata?.getString(0).equals("0")){
            checkbox_nata.visibility = View.GONE
            textView1.visibility= View.GONE
        }else{
            val cursor_user2: Cursor? = addOnsRepository?.getListProduct("NATA")
            cursor_user2?.moveToNext()
            textView1.setText(cursor_user2?.getString(3))
            if(cursor_user2?.getString(2).equals("NATA")) {
                checkbox_nata.visibility = View.VISIBLE
                price = cursor_user2?.getString(3).toString().toDouble().roundToInt()
                checkbox_nata.setOnClickListener {
                    if (checkbox_nata.isChecked) {
                        totalamount += price
                        text_price.setText(totalamount.toString()+".00")
                        result = result + " NATA - ";
                    }else {
                        totalamount -= price
                        text_price.setText(totalamount.toString()+".00")
                        result = result.replace(" NATA - ","")
                    }
                }
            }else {
                checkbox_nata.visibility = View.GONE
                textView1.visibility= View.GONE
            }
        }



// oreo

        val cursor_user_oreo2count: Cursor? = addOnsRepository?.getListAddCount("OREO")
        cursor_user_oreo2count?.moveToNext()

        val cursor_user_oreo2: Cursor? = addOnsRepository?.getListProduct("OREO")
        cursor_user_oreo2?.moveToNext()

        if(cursor_user_oreo2count?.getString(0).equals("0")){
            checkbox_oreo.visibility = View.GONE
            textView2.visibility= View.GONE
        }else{
            textView2.setText(cursor_user_oreo2!!.getString(3))
            if(cursor_user_oreo2?.getString(2).equals("OREO")) {
                checkbox_oreo.visibility = View.VISIBLE
                price1 = cursor_user_oreo2?.getString(3).toString().toDouble().roundToInt()
                checkbox_oreo.setOnClickListener {
                    if (checkbox_oreo.isChecked) {
                        totalamount += price1
                        text_price.setText(totalamount.toString()+".00")
                        result = result + " OREO - ";
                    }else {
                        totalamount -= price1
                        text_price.setText(totalamount.toString()+".00")
                        result = result.replace(" OREO - ","")
                    }
                }
            }else {
                checkbox_oreo.visibility = View.GONE
                textView2.visibility= View.GONE
            }

        }





        // oreo end
        //Start of syrup
        val cursor_user_syrupcount: Cursor? = addOnsRepository?.getListAddCount("SYRUP")
        cursor_user_syrupcount?.moveToNext()

        val cursor_user_syrup: Cursor? = addOnsRepository?.getListProduct("SYRUP")
        cursor_user_syrup?.moveToNext()

        if(cursor_user_syrupcount?.getString(0).equals("0")){
            checkbox_syrup.visibility = View.GONE
            textView3.visibility= View.GONE
        }else{
            textView3.setText(cursor_user_syrup!!.getString(3))
            if(cursor_user_syrup?.getString(2).equals("SYRUP")) {
                checkbox_syrup.visibility = View.VISIBLE
                price2 = cursor_user_syrup?.getString(3).toString().toDouble().roundToInt()
                checkbox_syrup.setOnClickListener {
                    if (checkbox_syrup.isChecked) {
                        totalamount += price2
                        text_price.setText(totalamount.toString()+".00")
                        result = result + " SYRUP - ";
                    }else {
                        totalamount -= price2
                        text_price.setText(totalamount.toString()+".00")
                        result = result.replace(" SYRUP - ","")
                    }
                }
            }else {
                checkbox_syrup.visibility = View.GONE
                textView3.visibility= View.GONE
            }
        }




        //end of syrup

        //start of boba
        val cursor_user_boba_count: Cursor? = addOnsRepository?.getListAddCount("POPPING BOBA")
        cursor_user_boba_count?.moveToNext()


        val cursor_user_boba: Cursor? = addOnsRepository?.getListProduct("POPPING BOBA")
        cursor_user_boba?.moveToNext()

        if(cursor_user_boba_count?.getString(0).equals("0")){
            checkbox_poppingboba.visibility = View.GONE
            textView4.visibility= View.GONE
        }else{

            if(cursor_user_boba?.getString(2).equals("POPPING BOBA")) {
                textView4.setText(cursor_user_boba?.getString(3))
                checkbox_poppingboba.visibility = View.VISIBLE
                price3 = cursor_user_boba?.getString(3).toString().toDouble().roundToInt()
                checkbox_poppingboba.setOnClickListener {
                    if (checkbox_poppingboba.isChecked) {
                        totalamount += price3
                        text_price.setText(totalamount.toString()+".00")
                        result = result + " POPPING BOBA - ";
                    }else {
                        totalamount -= price3
                        text_price.setText(totalamount.toString()+".00")
                        result = result.replace(" POPPING BOBA - ","")
                    }
                }
            }else {
                checkbox_poppingboba.visibility = View.GONE
                textView4.visibility= View.GONE
            }


        }



//end of boba

        //Start of cream
        val cursor_user_cream_count: Cursor? = addOnsRepository?.getListAddCount("WHIPPED CREAM")
        cursor_user_cream_count?.moveToNext()

        val cursor_user_cream: Cursor? = addOnsRepository?.getListProduct("WHIPPED CREAM")
        cursor_user_cream?.moveToNext()

        if(cursor_user_cream_count?.getString(0).equals("0")){
            checkbox_whippedcream.visibility = View.GONE
            textView5.visibility= View.GONE
        }else{

            if(cursor_user_cream?.getString(2).equals("WHIPPED CREAM")) {
                textView5.setText(cursor_user_cream!!.getString(3))
                checkbox_whippedcream.visibility = View.VISIBLE
                price4 = cursor_user_cream?.getString(3).toString().toDouble().roundToInt()
                checkbox_whippedcream.setOnClickListener {
                    if (checkbox_whippedcream.isChecked) {
                        totalamount += price4
                        text_price.setText(totalamount.toString()+".00")
                        result = result + " WHIPPED CREAM - ";
                    }else {
                        totalamount -= price4
                        text_price.setText(totalamount.toString()+".00")
                        result = result.replace(" WHIPPED CREAM - ","")
                    }
                }
            }else {
                checkbox_whippedcream.visibility = View.GONE
                textView5.visibility= View.GONE
            }
        }



//end of cream


        //start of shot

        val cursor_user_shot_count: Cursor? = addOnsRepository?.getListAddCount("ESPRESSO SHOT")
        cursor_user_shot_count?.moveToNext()


        val cursor_user_shot: Cursor? = addOnsRepository?.getListProduct("ESPRESSO SHOT")
        cursor_user_shot?.moveToNext()

        if(cursor_user_shot_count?.getString(0).equals("0")){
            checkbox_espesso.visibility = View.GONE
            textView6.visibility= View.GONE
            }else{
            textView6.setText(cursor_user_shot!!.getString(3))
            if(cursor_user_shot?.getString(2).equals("ESPRESSO SHOT")) {
                checkbox_espesso.visibility = View.VISIBLE
                price5 = cursor_user_shot?.getString(3).toString().toDouble().roundToInt()
                checkbox_espesso.setOnClickListener {
                    if (checkbox_espesso.isChecked) {
                        totalamount += price5
                        text_price.setText(totalamount.toString()+".00")
                        result = result + " ESPRESSO SHOT - ";
                    }else {
                        totalamount -= price5
                        text_price.setText(totalamount.toString()+".00")
                        result = result + " ";
                    }
                }
            }else {
                checkbox_espesso.visibility = View.GONE
                textView6.visibility= View.GONE
            }

        }



        //end of shot


        btn_add.setOnClickListener {
//            Toast.makeText(this,value_quantity+cartTotal.text.toString(),Toast.LENGTH_LONG).show()
            if(cartTotal.text.toString().equals("0")){
                Toast.makeText(this@OrderItem2,"PLEASE INDICATE THE QUANTITY FIRST",Toast.LENGTH_LONG).show()
            }else{
                val intent = getIntent();
                val myValue = intent.getStringExtra("product_image")
                val myProduct = intent.getStringExtra("product_name")
                var myProducprices = intent.getStringExtra("product_price")
                var product_code = intent.getStringExtra("product_code")
                SweetAlertDialog(this@OrderItem2, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("CONTINUE  THIS ADD CART THIS ITEM?")
                    .setConfirmText("CONFIRM!")
                    .setConfirmClickListener { sDialog ->
                       var price_s = text_price.text.toString().toDouble()
                        var quantity_s = cartTotal.text.toString().toDouble()
                        var final_total = price_s.toDouble() * quantity_s.toDouble()
                        val add_cart = CustomerDataOrder(
                            0,
                            myProduct.toString(),
                            cartTotal.text.toString(),
                            text_price.text.toString(),
                            result.toString(),
                            value_radio.toString(),
                            final_total.toDouble().toString(),
                            product_code.toString()
                        )
                        customerDataOrderViewModel.insert(add_cart)
                        startActivity(
                            Intent(
                                applicationContext.applicationContext,
                                com.jcreates.coffeev3.TabLayout.Dashboard::class.java
                            )
                        )
                        Toast.makeText(this@OrderItem2,"ORDER ADDED TO CART",Toast.LENGTH_LONG).show()
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                        sDialog.dismissWithAnimation()
                    }
                    .setCancelButton(
                        "Cancel"
                    ) { sDialog ->
                        Toast.makeText(
                            this.applicationContext,
                            "Cancel To Add Cart  !",
                            Toast.LENGTH_LONG
                        ).show()
                        sDialog.dismissWithAnimation()
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




}