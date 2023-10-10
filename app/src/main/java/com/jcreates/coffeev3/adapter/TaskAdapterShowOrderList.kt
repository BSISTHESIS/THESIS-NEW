package com.jcreates.coffeev3.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jcreates.coffeev3.DialogFragment.ReceiptDialogFragment
import com.jcreates.coffeev3.DialogFragment.ReceiptReprintDialogFragment
import com.jcreates.coffeev3.LoadingDialog
import com.jcreates.coffeev3.R
import com.jcreates.coffeev3.data.entity.Cashier
import com.jcreates.coffeev3.data.entity.CashierListOrder
import com.jcreates.coffeev3.data.entity.Popular
import com.joey.kotlinandroidbeginning.API.RetroCallServer

import java.lang.String
import kotlin.Boolean
import kotlin.ByteArray
import kotlin.Int

class TaskAdapterShowOrderList(val context: Context, val activity: Activity,val fragment: FragmentManager) : RecyclerView.Adapter<TaskAdapterShowOrderList.TaskAdapterViewHolder>(){
    var bitmap: Bitmap? = null
    private var callServer: RetroCallServer? = null
    var items  = ArrayList<CashierListOrder>()

    fun setListData(data: List<CashierListOrder>) {
        this.items = data as ArrayList<CashierListOrder>
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapterViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_recyler_dash_cashier,parent,false)
        return TaskAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskAdapterViewHolder, position: Int) {
        callServer = RetroCallServer(activity)
        val loading = LoadingDialog(activity)
        val user = items[position]
//        val decodedString: ByteArray =
//            Base64.decode(String.valueOf(user.product_image), Base64.DEFAULT)
//
//        Glide.with(context).asBitmap().load(decodedString).optionalCircleCrop()
//            .into(holder.imageView)

//        val decodedString: ByteArray =
//            Base64.decode(String.valueOf(user.product_image), Base64.DEFAULT)
//        val pixel1 = 1000
//        val pixel2 = 1000
//        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
//        this.bitmap = Bitmap.createScaledBitmap(decodedByte, pixel1, pixel2, true)
//        holder.imageView.setAdjustViewBounds(true)
//        holder.imageView.setDrawingCacheEnabled(true)
//        holder.imageView.getAdjustViewBounds()
//        holder.imageView.isHardwareAccelerated()
//        holder.imageView.setImageBitmap(bitmap)
//        holder.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE

//        holder.text_name.text = "PRODUCT NAME :"+user.product_name
//        holder.text_price.text = "PRICE :"+user.product_price
//        if(user.product_status.equals("ACTIVE")){
//            holder.text_status.text = "PRODUCT STATUS : AVAILABLE"
//            holder.text_status.setTextColor(Color.parseColor("#B3F4BD"))
//        }else{
//            holder.text_status.text = "PRODUCT STATUS :UNAVAILABLE"
//            holder.text_status.setTextColor(Color.parseColor("#E1EFE3"))
//        }
//        holder.btn_edit_status.setOnClickListener {
//          callServer!!.RequestUpdateStatusPopular(user.product_id)
//        }
//
//        holder.btn_delete.setOnClickListener {
//           callServer!!.RequestUpdateStatusPopular2(user.product_id)
//        }

        holder.order_no.text = "ORDER NUMBER: "+user.order_no
        holder.name_order.text = "CUSTOMER NAME: "+user.name_order
        holder.order_level.text = "ORDER DETAIL: "+user.order_level


        if(user.order_status.equals("PAID")){
            holder.order_status.text  ="ORDER STATUS: "+user.order_status
            holder.order_status.setTextColor(Color.parseColor("#B3F4BD"))
            holder.btn_change.visibility=View.GONE
            holder.btn_cancel_order.visibility=View.VISIBLE
        }else{
            holder.order_status.text  ="ORDER STATUS: "+user.order_status
            holder.order_status.setTextColor(Color.parseColor("#D50000"))

            holder.btn_change.visibility=View.VISIBLE
            holder.btn_cancel_order.visibility=View.GONE

        }

        holder.btn_change.setOnClickListener {
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object :Runnable {
                override fun run() {
                    val args = Bundle()
                    val userPopUp = ReceiptDialogFragment()
                    userPopUp.setArguments(args)
                    if (fragment != null) {
                        loading.isDismiss()
                        args.putString("order_no", user.order_no)
                        userPopUp.show(fragment, "my_dialog")
                    }
                    loading.isDismiss()
                }
            },2000)
        }

        holder.btn_cancel_order.setOnClickListener {
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object :Runnable {
                override fun run() {
                    val args = Bundle()
                    val userPopUp = ReceiptReprintDialogFragment()
                    userPopUp.setArguments(args)
                    if (fragment != null) {
                        loading.isDismiss()
                        args.putString("order_no", user.order_no)
                        args.putString("payment", user.payment)
                        args.putString("money", user.money)
                        args.putString("final_change", user.final_change)
                        userPopUp.show(fragment, "my_dialog")
                    }
                    loading.isDismiss()
                }
            },2000)


        }

    }
    private val differCallback = object : DiffUtil.ItemCallback<Popular>(){
        override fun areItemsTheSame(oldItem: Popular, newItem: Popular): Boolean {
            return  oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Popular, newItem: Popular): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)
    override fun getItemCount() = items.size

    class TaskAdapterViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        var  order_no = itemView.findViewById<TextView>(R.id.order_no)
        var  name_order = itemView.findViewById<TextView>(R.id.name_order)
        var  btn_cancel_order = itemView.findViewById<Button>(R.id.btn_cancel_order)
        var  btn_change = itemView.findViewById<Button>(R.id.btn_change)
        var order_status = itemView.findViewById<TextView>(R.id.order_status)
        var order_level = itemView.findViewById<TextView>(R.id.order_level)

    }

}