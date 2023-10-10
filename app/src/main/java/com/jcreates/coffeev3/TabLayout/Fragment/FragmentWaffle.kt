package com.jcreates.coffeev3.TabLayout.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jcreates.coffeev3.R
import com.jcreates.coffeev3.adapter.TaskAdapterShowOrderWaffle

import com.joey.noteapplication.data.viewModel.WaffleViewModel
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

class FragmentWaffle : Fragment() {
    // TODO: Rename and change types of parameters
    private var taskRecyclerView: RecyclerView? = null
    private lateinit var waffleViewModel: WaffleViewModel
    private  var taskAdapter: TaskAdapterShowOrderWaffle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_waffle, container, false)
        uiComponent(view)
        functions()
        return view
    }

    fun uiComponent(view:View){
        taskRecyclerView = view.findViewById<RecyclerView>(R.id.RecylerHistory)
        val manager = LinearLayoutManager(context)
        taskRecyclerView?.setLayoutManager(manager)
        taskRecyclerView?.setLayoutManager(GridLayoutManager(requireContext().applicationContext, 2))
        taskRecyclerView?.setHasFixedSize(true)
        taskAdapter = TaskAdapterShowOrderWaffle(requireActivity().applicationContext,requireActivity())
        taskRecyclerView?.setAdapter(taskAdapter)

        waffleViewModel = ViewModelProvider(this).get(WaffleViewModel::class.java)

        waffleViewModel?.allnotes2?.observe(viewLifecycleOwner, Observer { user->
            taskAdapter?.setListData(user)
            taskAdapter?.notifyDataSetChanged()
        })
    }

    fun functions(){

    }

    fun changeDateFormatFromAnotherz(date: String?): String? {
        @SuppressLint("SimpleDateFormat") val inputFormat: DateFormat =
            SimpleDateFormat("yyyy-MM-dd")
        @SuppressLint("SimpleDateFormat") val outputFormat: DateFormat =
            SimpleDateFormat("MMMM dd, yyyy")
        var resultDate: String? = ""
        try {
            resultDate = outputFormat.format(inputFormat.parse(date))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return resultDate
    }

}


