package com.example.catdiary.screens.fragments.update

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.catdiary.R
import com.example.catdiary.databinding.FragmentUpdateBinding
import com.example.catdiary.screens.model.Log
import com.example.catdiary.screens.viewmodel.LogViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import java.text.SimpleDateFormat
import java.util.*


class UpdateFragment : Fragment(), MenuProvider {
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var binding: FragmentUpdateBinding
    private lateinit var mLogViewModel: LogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_update,container,false)

        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        binding.updateEventDropdown.setText(args.currentLog.event)
        val items = arrayOf("Food", "Medicine", "Stool/Pee", "Water", "Hairball", "Miscellaneous")
        (binding.updateEventDropdown as? MaterialAutoCompleteTextView)?.setSimpleItems(items)
        binding.timeTv2.text = args.currentLog.dateAndTime
        binding.updateQuantity.setText(args.currentLog.quantity)
        binding.updateQuantityType.text = args.currentLog.quantityType
        binding.updateComment.setText(args.currentLog.comment)

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
                    binding.timeTv2.text = date
                }, startHour, startMinute, false).show()
            }, startYear, startMonth, startDay).show()
        })

        binding.updateQuantityType.setOnClickListener(View.OnClickListener {
            var selectedItemIndex = 0
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
                    binding.updateQuantityType.text = selectedQuantityType
                }
                .setNeutralButton("Cancel"){ _, _ ->
                    //Respond to neutral button press
                }
                .show()
        })
        binding.updateBtn.setOnClickListener{
            updateItem()
        }

//        Add Menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    private fun updateItem(){
        val event = binding.updateEventDropdown.text.toString()
        val dateAndTime = binding.timeTv2.text.toString()
        val quantity = binding.updateQuantity.text.toString()
        val quantityType = binding.updateQuantityType.text.toString()
        val comment = binding.updateComment.text.toString()

        if(inputCheck( event, dateAndTime,quantity,quantityType, comment)) {
            //Create log Object
            val updatedLog = Log(args.currentLog.id, dateAndTime, event, quantity,quantityType,comment)
            //Update Current User
            mLogViewModel.updateLog(updatedLog)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            //Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_logEventFragment)

        }else{
            Toast.makeText(requireContext(),"Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSnackBar(msg: String){
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
    }

    private fun inputCheck(eventLog: String, dateAndTime: String, quantity: String,quantityType: String, comment: String): Boolean{
        return !(TextUtils.isEmpty(eventLog) && TextUtils.isEmpty(dateAndTime) &&  TextUtils.isEmpty(quantity) &&  TextUtils.isEmpty(quantityType)&& TextUtils.isEmpty(comment))
    }

    override fun onCreateMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }


    override fun onMenuItemSelected(item: MenuItem): Boolean {
        // Handle the menu selection
        if(item.itemId == R.id.menu_delete){

            deleteLog()
        }
        return super.onContextItemSelected(item)
    }

    private fun deleteLog(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _, _ ->
            mLogViewModel.deleteLog(args.currentLog)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentLog.event}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_logEventFragment)
        }
        builder.setNegativeButton("No"){ _, _ -> }
        builder.setTitle("Delete ${args.currentLog.event}?")
        builder.setMessage("Are you sure you want to delete ${args.currentLog.event}?")
        builder.create().show()
    }
}