package com.kenzie.kasircafe.adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kenzie.kasircafe.R
import com.kenzie.kasircafe.model.MenuModel

class menuAdapter (private val listmenu: ArrayList<MenuModel>) : RecyclerView.Adapter<menuAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int): menuAdapter.ViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }
    override fun onBindViewHolder(holder: menuAdapter.ViewHolder, position: Int) {
        val currentMenu = listmenu[position]
        holder.rvMenu.text = currentMenu.menuNama
        holder.rvHargaMenu.text = currentMenu.menuHarga
        holder.rvDescMenu.text = currentMenu.menuKet
    }

    override fun getItemCount(): Int {
        return listmenu.size
    }
    class ViewHolder(itemView: View, clickListener: onItemClickListener): RecyclerView.ViewHolder(itemView) {

        val rvMenu : TextView = itemView.findViewById(R.id.tvMenuName)
        val rvHargaMenu : TextView = itemView.findViewById(R.id.tvMenuharga)
        val rvDescMenu : TextView = itemView.findViewById(R.id.tvMenuDesc2)
        init{
            itemView.setOnClickListener{
                clickListener.onItemClick(absoluteAdapterPosition)
            }
        }
    }

}