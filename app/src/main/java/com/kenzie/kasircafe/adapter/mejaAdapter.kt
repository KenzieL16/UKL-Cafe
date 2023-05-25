package com.kenzie.kasircafe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kenzie.kasircafe.R
import com.kenzie.kasircafe.model.MejaModel
import java.text.FieldPosition

class mejaAdapter (private val listmeja: ArrayList<MejaModel>) : RecyclerView.Adapter<mejaAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int): mejaAdapter.ViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.meja_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }
    override fun onBindViewHolder(holder: mejaAdapter.ViewHolder, position: Int) {
        val currentMeja = listmeja[position]
        holder.rvMeja.text = currentMeja.mejaNama
    }

    override fun getItemCount(): Int {
        return listmeja.size
    }
    class ViewHolder(itemView: View, clickListener: onItemClickListener): RecyclerView.ViewHolder(itemView) {

        val rvMeja : TextView = itemView.findViewById(R.id.tvNamaMeja)
        init{
            itemView.setOnClickListener{
                clickListener.onItemClick(absoluteAdapterPosition)
            }
        }
    }

}