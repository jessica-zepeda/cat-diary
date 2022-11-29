package com.example.catdiary.screens.fragments.title

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.catdiary.R
import com.example.catdiary.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater,
            R.layout.fragment_title,container,false)
        binding.loginButton.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_loginFragment)
        }
        binding.noAccountText.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_signUpFragment)
        }
        return binding.root
    }

}