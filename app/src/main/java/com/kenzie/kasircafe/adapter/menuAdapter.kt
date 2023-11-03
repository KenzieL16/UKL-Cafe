package com.kenzie.kasircafe.adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kenzie.kasircafe.R
import com.kenzie.kasircafe.model.MenuModel

class menuAdapter(private val listmenu: ArrayList<MenuModel>, private val isAddTransaksi: Boolean) : RecyclerView.Adapter<menuAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMenu = listmenu[position]
        holder.rvMenu.text = currentMenu.menuNama
        holder.rvHargaMenu.text = currentMenu.menuHarga
        holder.rvDescMenu.text = currentMenu.menuKet

        if (isAddTransaksi) {
            holder.btnPlus.visibility = View.VISIBLE
            holder.btnMinus.visibility = View.VISIBLE
            holder.textJumlah.visibility = View.VISIBLE
        } else {
            holder.btnPlus.visibility = View.GONE
            holder.btnMinus.visibility = View.GONE
            holder.textJumlah.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return listmenu.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        val rvMenu : TextView = itemView.findViewById(R.id.tvMenuName)
        val rvHargaMenu : TextView = itemView.findViewById(R.id.tvMenuharga)
        val rvDescMenu : TextView = itemView.findViewById(R.id.tvMenuDesc2)
        val btnPlus: Button = itemView.findViewById(R.id.btnPlus)
        val btnMinus: Button = itemView.findViewById(R.id.btnMinus)
        val textJumlah: TextView = itemView.findViewById(R.id.textJumlah)

        init {
            itemView.setOnClickListener {
                if (clickListener != null) {
                    clickListener.onItemClick(absoluteAdapterPosition)
                }
            }
        }
    }
}