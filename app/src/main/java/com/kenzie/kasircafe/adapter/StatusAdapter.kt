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

class StatusAdapter(private val context: Context,
                    private val transaksiList: MutableList<TransaksiModel>
) : RecyclerView.Adapter<StatusAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTransaksiMenu: TextView = itemView.findViewById(R.id.tvTransaksiMenu)
        val tvSelectedMeja: TextView = itemView.findViewById(R.id.tvSelectedMeja)
        val tvMetodePembayaran: TextView = itemView.findViewById(R.id.tvMetodePembayaran)
        val tvTanggalTransaksi: TextView = itemView.findViewById(R.id.tvTanggalTransaksi)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHargaMenu)
        val btnSelesai: Button = itemView.findViewById(R.id.btnSelesaiTransaksi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_status_pembayaran, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = transaksiList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = transaksiList[position]
        holder.tvTransaksiMenu.text = currentItem.menu
        holder.tvSelectedMeja.text = currentItem.selectedMeja
        holder.tvMetodePembayaran.text = currentItem.selectedPayment
        holder.tvTanggalTransaksi.text = currentItem.tanggal
        holder.tvHarga.text = currentItem.harga

        // Atur visibilitas tombol "Selesai" berdasarkan isSelesaiButtonVisible
        if (currentItem.isSelesaiButtonVisible) {
            holder.btnSelesai.visibility = View.VISIBLE
        } else {
            holder.btnSelesai.visibility = View.GONE
        }

        holder.btnSelesai.setOnClickListener {
            // Mengatur properti isSelesai menjadi true
            transaksiList[position].isSelesai = true

            // Menyimpan perubahan ke Firebase Realtime Database
            val transaksiRef = FirebaseDatabase.getInstance().getReference("transaksi")
                .child(currentItem.idTransaksi)
                .child("selesai")

            transaksiRef.setValue(true)
                .addOnSuccessListener {
                    // Item sudah ditandai sebagai selesai dan disimpan ke riwayat
                    Toast.makeText(context, "Transaksi selesai", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    // Gagal menandai transaksi sebagai selesai
                    Toast.makeText(
                        context,
                        "Gagal menandai transaksi sebagai selesai",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}
