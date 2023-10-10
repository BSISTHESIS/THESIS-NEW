package com.jcreates.coffeev3.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcreates.coffeev3.R
import com.jcreates.coffeev3.data.entity.CustomerDataOrder
import com.jcreates.coffeev3.data.repository.CustomerDataOrderRepository
import com.joey.kotlinandroidbeginning.API.RetroCallServer

import kotlin.Boolean
import kotlin.Int

class TaskAdapterOrderList(val context: Context, val activity: Activity) : RecyclerView.Adapter<TaskAdapterOrderList.TaskAdapterViewHolder>(){
    var bitmap: Bitmap? = null
    private var callServer: RetroCallServer? = null
    private var customerDataOrderRepository:CustomerDataOrderRepository ?=null
    var items  = ArrayList<CustomerDataOrder>()

    fun setListData(data: List<CustomerDataOrder>) {
        this.items = data as ArrayList<CustomerDataOrder>
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapterViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_order_list,parent,false)
        return TaskAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskAdapterViewHolder, position: Int) {
        callServer = RetroCallServer(activity)
        customerDataOrderRepository = CustomerDataOrderRepository(activity.application)
        val user = items[position]

        holder.btn_remove.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("DELETE THIS ORDER?")
                .setCancelable(false)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(
                    android.R.string.yes
                ) { arg0, arg1 ->
                    customerDataOrderRepository!!.delteOrder(user.id.toString())
                }.create().show()
        }
        holder.text_name.text = "PRODUCT NAME :"+user.product_name
        holder.text_price.text = "PRICE :"+user.product_price
        holder.txt_sugar.text= "SUGAR PERCENTAGE: "+user.sugar_percent
        holder.txt_addons.text ="ADD ONS : "+ user.add_ons
        holder.quantity.text = user.quantity
        holder.price_.text = user.product_price
        holder.total.text = user.total_price

        if(user.product_code.equals("code_non_coffee")){
            holder.txt_sugar.visibility = View.GONE
            holder.txt_addons.visibility = View.GONE
        }
    }
    private val differCallback = object : DiffUtil.ItemCallback<CustomerDataOrder>(){
        override fun areItemsTheSame(oldItem: CustomerDataOrder, newItem: CustomerDataOrder): Boolean {
            return  oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CustomerDataOrder, newItem: CustomerDataOrder): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)
    override fun getItemCount() = items.size

    class TaskAdapterViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var  text_name = itemView.findViewById<TextView>(R.id.text_name)
        var  text_price = itemView.findViewById<TextView>(R.id.product_price)
        var  btn_remove = itemView.findViewById<Button>(R.id.btn_delete)
        var txt_addons = itemView.findViewById<TextView>(R.id.product_status)
        var txt_sugar = itemView.findViewById<TextView>(R.id.product_sugar)
        var quantity = itemView.findViewById<TextView>(R.id.quantity)
        var price_ = itemView.findViewById<TextView>(R.id.product_price2)
        var total = itemView.findViewById<TextView>(R.id.total)
    }

}