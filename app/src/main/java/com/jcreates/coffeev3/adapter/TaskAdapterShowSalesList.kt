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
import com.jcreates.coffeev3.data.entity.SalesList
import com.joey.kotlinandroidbeginning.API.RetroCallServer

import java.lang.String
import java.text.DecimalFormat
import kotlin.Boolean
import kotlin.ByteArray
import kotlin.Int

class TaskAdapterShowSalesList(val context: Context, val activity: Activity, val fragment: FragmentManager) : RecyclerView.Adapter<TaskAdapterShowSalesList.TaskAdapterViewHolder>(){
    var bitmap: Bitmap? = null
    private var callServer: RetroCallServer? = null
    var items  = ArrayList<SalesList>()

    fun setListData(data: List<SalesList>) {
        this.items = data as ArrayList<SalesList>
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapterViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_recyler_dash_sales,parent,false)
        return TaskAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskAdapterViewHolder, position: Int) {
        callServer = RetroCallServer(activity)
        val loading = LoadingDialog(activity)
        val user = items[position]

        holder.order_no.text = "ORDER NUMBER: "+user.order_no
        holder.name_order.text = "CUSTOMER NAME: "+user.name_order
        holder.order_date.text = "SCHEDULE DATE: "+user.scheduled_date


        var payment1: Double? = user.payment?.toString()?.toDouble()
        var formatters2 = DecimalFormat("#,###.00")
        var final_pay = formatters2.format(payment1)

        var payment2: Double? = user.money?.toString()?.toDouble()
        var formatters3 = DecimalFormat("#,###.00")
        var final_money = formatters3.format(payment2)


        var payment3: Double? = user.final_change?.toDouble()
        var formatters4 = DecimalFormat("#,###.00")
        var final_changes = formatters4.format(payment3)


        holder.payment.text = "PAYMENT: PHP "+final_pay
        holder.money.text = "MONEY: PHP "+final_money
        holder.change.text = "CHANGE: PHP "+final_changes

        if(user.order_status.equals("PAID")){
            holder.order_status.text  ="ORDER STATUS: "+user.order_status
            holder.order_status.setTextColor(Color.parseColor("#B3F4BD"))

            holder.btn_cancel_order.visibility=View.GONE
        }else{
            holder.order_status.text  ="ORDER STATUS: "+user.order_status
            holder.order_status.setTextColor(Color.parseColor("#D50000"))

            holder.btn_cancel_order.visibility=View.GONE

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
        var order_status = itemView.findViewById<TextView>(R.id.order_status)
        var order_level = itemView.findViewById<TextView>(R.id.order_level)
        var order_date = itemView.findViewById<TextView>(R.id.order_date)
        var payment = itemView.findViewById<TextView>(R.id.payment_order)
        var money = itemView.findViewById<TextView>(R.id.money)
        var change = itemView.findViewById<TextView>(R.id.change)
    }

}