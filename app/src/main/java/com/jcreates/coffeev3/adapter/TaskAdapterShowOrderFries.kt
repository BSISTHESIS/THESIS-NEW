package com.jcreates.coffeev3.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jcreates.coffeev3.ORDERSTATUS.OrderItem2
import com.jcreates.coffeev3.R
import com.jcreates.coffeev3.data.entity.Fries

import com.joey.kotlinandroidbeginning.API.RetroCallServer

import java.lang.String
import kotlin.Boolean
import kotlin.ByteArray
import kotlin.Int

class TaskAdapterShowOrderFries(val context: Context, val activity: Activity) : RecyclerView.Adapter<TaskAdapterShowOrderFries.TaskAdapterViewHolder>(){
    var bitmap: Bitmap? = null
    private var callServer: RetroCallServer? = null
    var items  = ArrayList<Fries>()

    fun setListData(data: List<Fries>) {
        this.items = data as ArrayList<Fries>
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapterViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_recyler_dash_order,parent,false)
        return TaskAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskAdapterViewHolder, position: Int) {
        callServer = RetroCallServer(activity)
        val user = items[position]
        val decodedString: ByteArray =
            Base64.decode(String.valueOf(user.product_image), Base64.DEFAULT)


        Glide.with(context).asBitmap().load(decodedString).override(
            GridLayout.LayoutParams.MATCH_PARENT,
            GridLayout.LayoutParams.MATCH_PARENT)
            .into(holder.imageView)
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
//        holder.imageView.scaleType = ImageView.ScaleType.FIT_XY

        holder.text_name.text = ""+user.product_name
        holder.text_price.text = "₱ "+user.product_price
        if(user.product_status.equals("ACTIVE")){
            holder.card_view_next_fragment.isEnabled = true
            holder.text_status.text = " STATUS : AVAILABLE"
            holder.text_status.setTextColor(Color.parseColor("#247b37"))
        }else{
            holder.card_view_next_fragment.isEnabled = false
            holder.text_status.text = " STATUS :NOT AVAILABLE"
            holder.text_status.setTextColor(Color.parseColor("#000000"))
        }
        holder.btn_edit_status.setOnClickListener {
          callServer!!.RequestUpdateStatusFries(user.product_id)
        }

        holder.btn_delete.setOnClickListener {
           callServer!!.RequestUpdateStatusFries2(user.product_id)
        }

        holder.card_view_next_fragment.setOnClickListener {
            val intent = Intent(context, OrderItem2::class.java)
            intent.putExtra("product_image", user.product_image)
            intent.putExtra("product_name", user.product_name)
            intent.putExtra("product_price", user.product_price)
            intent.putExtra("product_code", user.product_code)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)

        }
    }
    private val differCallback = object : DiffUtil.ItemCallback<Fries>(){
        override fun areItemsTheSame(oldItem: Fries, newItem: Fries): Boolean {
            return  oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Fries, newItem: Fries): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)
    override fun getItemCount() = items.size

    class TaskAdapterViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var imageView = itemView.findViewById<ImageView>(R.id.image_view)
        var  text_name = itemView.findViewById<TextView>(R.id.text_name)
        var  text_price = itemView.findViewById<TextView>(R.id.product_price)
        var  btn_edit_status = itemView.findViewById<Button>(R.id.btn_edit_status)
        var text_status = itemView.findViewById<TextView>(R.id.product_status)
        var btn_delete = itemView.findViewById<Button>(R.id.btn_delete)
        var card_view_next_fragment = itemView.findViewById<CardView>(R.id.card_view_next_fragment)

    }

}