package com.jcreates.coffeev3.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcreates.coffeev3.DialogFragment.InsertNameCustomer
import com.jcreates.coffeev3.DialogFragment.ReceiptReprintDialogFragment
import com.jcreates.coffeev3.R
import com.jcreates.coffeev3.Splash.Start
import com.jcreates.coffeev3.data.entity.Popular
import com.jcreates.coffeev3.data.entity.SlideImage
import com.joey.kotlinandroidbeginning.API.RetroCallServer

import java.lang.String
import kotlin.Boolean
import kotlin.ByteArray
import kotlin.Int

class TaskAdapterShowImageSlide(val context: Context, val activity: Activity,val fragment: FragmentManager) : RecyclerView.Adapter<TaskAdapterShowImageSlide.TaskAdapterViewHolder>(){
    var bitmap: Bitmap? = null
    private var callServer: RetroCallServer? = null
    var items  = ArrayList<SlideImage>()

    fun setListData(data: List<SlideImage>) {
        this.items = data as ArrayList<SlideImage>
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapterViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_container,parent,false)
        return TaskAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskAdapterViewHolder, position: Int) {
        callServer = RetroCallServer(activity)
        val user = items[position]
        val decodedString: ByteArray =
            Base64.decode(String.valueOf(user.slide_image), Base64.DEFAULT)
        val pixel1 = 1000
        val pixel2 = 1000
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        this.bitmap = Bitmap.createScaledBitmap(decodedByte, pixel1, pixel2, true)
        holder.imageView.setAdjustViewBounds(true)
        holder.imageView.setDrawingCacheEnabled(true)
        holder.imageView.getAdjustViewBounds()
        holder.imageView.isHardwareAccelerated()
        holder.imageView.setImageBitmap(bitmap)
        holder.imageView.scaleType = ImageView.ScaleType.FIT_XY


        holder.imageView.setOnClickListener {
//            val intent = Intent(context, Start::class.java)
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context.startActivity(intent)

//            val args = Bundle()
//            val userPopUp = InsertNameCustomer()
//            userPopUp.setArguments(args)
//            supportFragmentManager
//            userPopUp.show(supportFragmentManager, "my_dialog")

            val args = Bundle()
            val userPopUp = InsertNameCustomer()
            userPopUp.setArguments(args)
            if (fragment != null) {
                userPopUp.show(fragment, "my_dialog")
            }
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
        val imageView: ImageView = itemView.findViewById(R.id.imageView);
    }

}