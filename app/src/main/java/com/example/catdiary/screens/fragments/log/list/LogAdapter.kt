package com.example.catdiary.screens.fragments.log.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.catdiary.R
import com.example.catdiary.screens.model.Log

class LogAdapter : RecyclerView.Adapter<LogAdapter.MyViewHolder>(){

    private var logList = emptyList<Log>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        val idView: TextView
//        val quantityView: TextView
        val dateView: TextView
        val eventView: TextView
        val rowLayout: ConstraintLayout

        init {
            // Define click listener for the ViewHolder's View.
//            idView = itemView.findViewById(R.id.id_txt)
//            quantityView = itemView.findViewById(R.id.age_txt)
            dateView = itemView.findViewById(R.id.dateAndTime)
            eventView = itemView.findViewById(R.id.eventTxt)
            rowLayout = itemView.findViewById(R.id.rowLayout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_row, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return logList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = logList[position]
//        holder.idView.text = currentItem.id.toString()
//        holder.quantityView.text = currentItem.age.toString()

        holder.dateView.text = currentItem.dateAndTime
        holder.eventView.text = currentItem.event

//        holder.rowLayout.setOnClickListener{
//            val action = LogEventFragmentDirections.actionLogEventFragmentToUpdateFragment(currentItem)
//            holder.itemView.findNavController().navigate(action)
//        }
    }

    fun setData(log: List<Log>){
        this.logList = log
        notifyDataSetChanged()
    }
}