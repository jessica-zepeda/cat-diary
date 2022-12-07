package com.example.catdiary.screens.fragments.log.add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.catdiary.R
import com.example.catdiary.databinding.FragmentAddBinding
import com.example.catdiary.screens.model.Log
import com.example.catdiary.screens.viewmodel.LogViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment() {

    private lateinit var mLogViewModel: LogViewModel
    private lateinit var binding: FragmentAddBinding
//    private var formatDate = SimpleDateFormat("dd MMMM YYYY", Locale.US)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add,container,false)

        mLogViewModel  = ViewModelProvider(this).get(LogViewModel::class.java)


        binding.pickTimeBtn.setOnClickListener(View.OnClickListener{
            val currentDateTime = Calendar.getInstance()
            val startYear = currentDateTime.get(Calendar.YEAR)
            val startMonth = currentDateTime.get(Calendar.MONTH)
            val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
            val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentDateTime.get(Calendar.MINUTE)

              DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, day ->
                TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                    val pickedDateTime = Calendar.getInstance()
                    pickedDateTime.set(year, month, day, hour, minute)
                    val date = SimpleDateFormat("MMM-dd-yyyy h:mm a").format(pickedDateTime.time)
                    Toast.makeText(requireContext(), "Date: " +  date, Toast.LENGTH_SHORT).show()
                    binding.timeTv.text = date
                }, startHour, startMinute, false).show()
            }, startYear, startMonth, startDay).show()
        })

        binding.addBtn.setOnClickListener {
            insertDataToDatabase()
        }
        return binding.root
    }

    private fun insertDataToDatabase() {
        val eventLog = binding.addEvent.text.toString()
        val dateAndTime = binding.timeTv.text.toString()
        val quantity = binding.addQuantity.text.toString()
        val comment = binding.addComment.text.toString()

        if(inputCheck(eventLog,dateAndTime,quantity, comment)){
            // Create Log Object
            val log = Log(0,dateAndTime,eventLog,quantity, comment)

            //Add Data to Database
            mLogViewModel.addLog(log)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()

            //Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_logEventFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(eventLog: String, dateAndTime: String, quantity: String, comment: String): Boolean{
        return !(TextUtils.isEmpty(eventLog) && TextUtils.isEmpty(dateAndTime) &&  TextUtils.isEmpty(quantity) && TextUtils.isEmpty(comment))
    }

}