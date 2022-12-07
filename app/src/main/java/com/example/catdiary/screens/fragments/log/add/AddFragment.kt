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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment() {

    private lateinit var mLogViewModel: LogViewModel
    private lateinit var binding: FragmentAddBinding
    var selectedItemIndex = 0
//    private var formatDate = SimpleDateFormat("dd MMMM YYYY", Locale.US)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add,container,false)

        mLogViewModel  = ViewModelProvider(this).get(LogViewModel::class.java)
        val items = arrayOf("Food", "Medicine", "Stool/Pee", "Water", "Hairball", "Miscellaneous")
        (binding.eventDropdownTextField.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(items)

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

        binding.typeQuantitySpinner.setOnClickListener(View.OnClickListener {
            val quantityType = arrayOf("Grams", "Cups", "Oz","Doses","Pieces","Pills")
            var selectedQuantityType = quantityType[selectedItemIndex]
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Quantity Type")
                .setSingleChoiceItems(quantityType, selectedItemIndex){ dialog, which ->
                    selectedItemIndex = which
                    selectedQuantityType = quantityType[which]
                }
                .setPositiveButton("Ok"){ _, _ ->
                    showSnackBar("$selectedQuantityType Selected")
                    binding.typeQuantitySpinner.text = selectedQuantityType
                }
                .setNeutralButton("Cancel"){ _, _ ->
                    //Respond to neutral button press
                }
                .show()
        })

        binding.addBtn.setOnClickListener {
            insertDataToDatabase()
        }
        return binding.root
    }

    private fun insertDataToDatabase() {
        val eventLog = binding.eventDropdownText.text.toString()
        val dateAndTime = binding.timeTv.text.toString()
        val quantity = binding.addQuantity.text.toString()
        val quantityType = binding.typeQuantitySpinner.text.toString()
        val comment = binding.addComment.text.toString()

        if(inputCheck(eventLog,dateAndTime,quantity,quantityType, comment)){
            // Create Log Object
            val log = Log(0,dateAndTime,eventLog,quantity,quantityType, comment)

            //Add Data to Database
            mLogViewModel.addLog(log)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()

            //Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_logEventFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(eventLog: String, dateAndTime: String, quantity: String, quantityType: String, comment: String): Boolean{
        return !(TextUtils.isEmpty(eventLog) && TextUtils.isEmpty(dateAndTime) &&  TextUtils.isEmpty(quantity)&&  TextUtils.isEmpty(quantityType) && TextUtils.isEmpty(comment))
    }

    private fun showSnackBar(msg: String){
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
    }

}