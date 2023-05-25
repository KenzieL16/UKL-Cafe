package com.kenzie.kasircafe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kenzie.kasircafe.R
import com.kenzie.kasircafe.model.EmailModel
import com.kenzie.kasircafe.model.MejaModel

class emailAdapter (private val listemail: ArrayList<EmailModel>) : RecyclerView.Adapter<emailAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int): emailAdapter.ViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.email_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }
    override fun onBindViewHolder(holder: emailAdapter.ViewHolder, position: Int) {
        val currentEmail = listemail[position]
        holder.rvEmail.text = currentEmail.email
    }

    override fun getItemCount(): Int {
        return listemail.size
    }
    class ViewHolder(itemView: View, clickListener: onItemClickListener): RecyclerView.ViewHolder(itemView) {

        val rvEmail : TextView = itemView.findViewById(R.id.tvEmail)
        init{
            itemView.setOnClickListener{
                clickListener.onItemClick(absoluteAdapterPosition)
            }
        }
    }

}
