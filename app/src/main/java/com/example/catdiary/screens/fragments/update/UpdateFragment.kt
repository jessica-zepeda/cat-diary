package com.example.catdiary.screens.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
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
import com.example.catdiary.screens.model.User
import com.example.catdiary.screens.viewmodel.UserViewModel


class UpdateFragment : Fragment(), MenuProvider {
   private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var binding: FragmentUpdateBinding
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_update,container,false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.updateFirstNameEt.setText(args.currentUser.firstName)
        binding.updateLastNameEt.setText(args.currentUser.lastName)
        binding.updateAgeEt.setText(args.currentUser.age.toString())

        binding.updateBtn.setOnClickListener{
            updateItem()
        }

        //Add Menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    private fun updateItem(){
        val firstName = binding.updateFirstNameEt.text.toString()
        val lastName = binding.updateLastNameEt.text.toString()
        val age = Integer.parseInt(binding.updateAgeEt.text.toString())

        if(inputCheck( firstName, lastName, binding.updateAgeEt.text)) {
            //Create User Object
            val updatedUser = User(args.currentUser.id, firstName, lastName, age)
            //Update Current User
            mUserViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            //Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_logEventFragment)

        }else{
            Toast.makeText(requireContext(),"Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

    override fun onCreateMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }


    override fun onMenuItemSelected(item: MenuItem): Boolean {
        // Handle the menu selection
        if(item.itemId == R.id.menu_delete){
//            deleteUser()
        }
        return true
//        return onMenuItemSelected(item)
    }

    private fun deleteUser(){
//        val builder = AlertDialog.Builder(requireContext())
//        builder.setPositiveButton("Yes"){ _ , _ ->
//            mUserViewModel.deleteUser(args.currentUser)
//            Toast.makeText(
//                requireContext(),
//                "Successfully removed: ${args.currentUser.firstName}",
//                Toast.LENGTH_SHORT).show()
//        }
//        builder.setNegativeButton("No"){_,_->}
//        builder.setTitle("Delete ${args.currentUser.firstName}?")
//        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName}")
//        builder.create().show()
    }
}