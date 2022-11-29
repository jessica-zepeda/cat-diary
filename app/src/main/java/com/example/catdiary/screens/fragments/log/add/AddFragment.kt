package com.example.catdiary.screens.fragments.log.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.catdiary.R
import com.example.catdiary.databinding.FragmentAddBinding
import com.example.catdiary.screens.model.User
import com.example.catdiary.screens.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var binding: FragmentAddBinding
    private var formatDate = SimpleDateFormat("dd MMMM YYYY", Locale.US)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add,container,false)

        mUserViewModel  = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.addBtn.setOnClickListener {
            insertDataToDatabase()

        }

        binding.datePickerButton.setOnClickListener(View.OnClickListener{
            val getDate: Calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(requireActivity(),android.R.style.Theme_Holo_Light_Dialog_MinWidth, DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->

                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, i)
                selectDate.set(Calendar.MONTH, i2)
                selectDate.set(Calendar.DAY_OF_MONTH, i3)
                val date = formatDate.format(selectDate.time)
                Toast.makeText(requireContext(), "Date: " +  date, Toast.LENGTH_SHORT).show()
                binding.datePickerButton.text = date
            },getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        })
        return binding.root
    }

    private fun insertDataToDatabase() {
        val firstName = binding.addFirstNameEt.text.toString()
        val lastName = binding.addLastNameEt.text.toString()
        val age = binding.addAge.text

        if(inputCheck(firstName,lastName,age)){
            // Create User Object
            val user = User(0,firstName,lastName,Integer.parseInt(age.toString()))

            //Add Data to Database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()

            //Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_logEventFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

}