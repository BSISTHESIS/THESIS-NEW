package com.jcreates.coffeev3.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jcreates.coffeev3.R
import com.jcreates.coffeev3.data.entity.Fries
import com.jcreates.coffeev3.data.entity.SlideImage
import com.joey.kotlinandroidbeginning.API.RetroCallServer

import java.lang.String
import kotlin.Boolean
import kotlin.ByteArray
import kotlin.Int

class TaskAdapterShowImage(val context: Context, val activity: Activity) : RecyclerView.Adapter<TaskAdapterShowImage.TaskAdapterViewHolder>(){
    var bitmap: Bitmap? = null
    private var callServer: RetroCallServer? = null
    var items  = ArrayList<SlideImage>()

    fun setListData(data: List<SlideImage>) {
        this.items = data as ArrayList<SlideImage>
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapterViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_recyler_dash_edit,parent,false)
        return TaskAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskAdapterViewHolder, position: Int) {
        callServer = RetroCallServer(activity)
        val user = items[position]
        val decodedString: ByteArray =
            Base64.decode(String.valueOf(user.slide_image), Base64.DEFAULT)

        Glide.with(context).asBitmap().load(decodedString).optionalCircleCrop()
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
//        holder.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE

        holder.text_name.visibility = View.GONE
        holder.text_price.visibility = View.GONE

        if(user.status.equals("ACTIVE")){
            holder.text_status.text = "PRODUCT STATUS : AVAILABLE"
            holder.text_status.setTextColor(Color.parseColor("#B3F4BD"))
        }else{
            holder.text_status.text = "PRODUCT STATUS :UNAVAILABLE"
            holder.text_status.setTextColor(Color.parseColor("#E1EFE3"))
        }
        holder.btn_edit_status.setOnClickListener {
          callServer!!.RequestUpdateStatusImageSlide(user.slide_id)
            Log.d("asasa",user.slide_id)
        }

        holder.btn_delete.setOnClickListener {
           callServer!!.RequestUpdateStatusImageSlide2(user.slide_id)
            Log.d("asasa",user.slide_id)
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
    }

}