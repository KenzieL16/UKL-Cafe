package com.kenzie.kasircafe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.kenzie.kasircafe.R
import com.kenzie.kasircafe.model.TransaksiModel

class pesananAdapter(
    private val context: Context,
    private val riwayatTransaksiList: List<TransaksiModel>
) : RecyclerView.Adapter<pesananAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRiwayatMenu: TextView = itemView.findViewById(R.id.tvTransaksiMenu)
        val tvSelectedMeja: TextView = itemView.findViewById(R.id.tvSelectedMeja)
        val tvMetodePembayaran: TextView = itemView.findViewById(R.id.tvMetodePembayaran)
        val tvTanggalTransaksi: TextView = itemView.findViewById(R.id.tvTanggalTransaksi)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHargaMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaksi_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = riwayatTransaksiList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = riwayatTransaksiList[position]
        holder.tvRiwayatMenu.text = currentItem.menu
        holder.tvSelectedMeja.text = currentItem.selectedMeja
        holder.tvMetodePembayaran.text = currentItem.selectedPayment
        holder.tvTanggalTransaksi.text = currentItem.tanggal
        holder.tvHarga.text = currentItem.harga
    }
}

