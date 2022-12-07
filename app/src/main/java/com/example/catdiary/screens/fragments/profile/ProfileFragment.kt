package com.example.catdiary.screens.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.catdiary.R
import com.example.catdiary.databinding.FragmentLoginBinding
import com.example.catdiary.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater,
            R.layout.fragment_profile,container,false)
        binding.editProfileBtn.setOnClickListener{ view : View ->
            view.findNavController().navigate(R.id.action_profileFragment_to_profileUpdateFragment)
        }
        return binding.root
    }

}