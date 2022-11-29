package com.example.catdiary.screens.fragments.log.list

import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.catdiary.R
import com.example.catdiary.screens.model.User

class LogAdapter : RecyclerView.Adapter<LogAdapter.MyViewHolder>(){

    private var userList = emptyList<User>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val idView: TextView
        val nameView: TextView
        val lastView: TextView
        val ageView: TextView
        val rowLayout: ConstraintLayout

        init {
            // Define click listener for the ViewHolder's View.
            idView = itemView.findViewById(R.id.id_txt)
            nameView = itemView.findViewById(R.id.firstName_txt)
            lastView = itemView.findViewById(R.id.lastName_txt)
            ageView = itemView.findViewById(R.id.age_txt)
            rowLayout = itemView.findViewById(R.id.rowLayout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_row, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.idView.text = currentItem.id.toString()
        holder.nameView.text = currentItem.firstName
        holder.lastView.text = currentItem.lastName
        holder.ageView.text = currentItem.age.toString()

        holder.rowLayout.setOnClickListener{
            val action = LogEventFragmentDirections.actionLogEventFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }
}