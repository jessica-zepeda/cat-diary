package com.example.catdiary.screens.fragments.profile

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.catdiary.R
import com.example.catdiary.databinding.FragmentProfileUpdateBinding
import java.text.SimpleDateFormat
import java.util.*


class ProfileUpdateFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProfileUpdateBinding>(inflater,
            R.layout.fragment_profile_update,container,false)

        binding.updateBday.setOnClickListener(View.OnClickListener{
            val currentDateTime = Calendar.getInstance()
            val startYear = currentDateTime.get(Calendar.YEAR)
            val startMonth = currentDateTime.get(Calendar.MONTH)
            val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    val pickedDateTime = Calendar.getInstance()
                    pickedDateTime.set(year, month, day)
                    val date = SimpleDateFormat("MMM dd, yyyy").format(pickedDateTime.time)
                    Toast.makeText(requireContext(), "Date: " +  date, Toast.LENGTH_SHORT).show()
                    binding.updateBday.text = date
            }, startYear, startMonth, startDay).show()
        })

        binding.updateBtn.setOnClickListener{ view : View ->
            view.findNavController().navigate(R.id.action_profileUpdateFragment_to_profileFragment)
        }
        return binding.root
    }


}