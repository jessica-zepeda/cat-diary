package com.example.catdiary.screens.fragments.log.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catdiary.R
import com.example.catdiary.databinding.FragmentLogEventBinding
import com.example.catdiary.screens.viewmodel.UserViewModel

class LogEventFragment : Fragment() {
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLogEventBinding>(inflater,
            R.layout.fragment_log_event,container,false)

        //Recyclerview
        val adapter = LogAdapter()
        val recyclerView =  binding.recyclerviewLog
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //UserViewModel
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
        })

        return binding.root
    }


}