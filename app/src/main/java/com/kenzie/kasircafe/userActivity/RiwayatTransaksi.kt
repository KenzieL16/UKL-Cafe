package com.kenzie.kasircafe.userActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.kenzie.kasircafe.R
import com.kenzie.kasircafe.adapter.pesananAdapter
import com.kenzie.kasircafe.addData.AddTransaksi
import com.kenzie.kasircafe.databinding.ActivityRiwayatTransaksiBinding
import com.kenzie.kasircafe.model.TransaksiModel

class RiwayatTransaksi : AppCompatActivity() { private lateinit var binding: ActivityRiwayatTransaksiBinding
    private lateinit var databaseReference: DatabaseReference
    private val riwayatTransaksiList = mutableListOf<TransaksiModel>()
    private lateinit var riwayatTransaksiAdapter: pesananAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_transaksi)
        binding = ActivityRiwayatTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("transaksi")
        riwayatTransaksiAdapter = pesananAdapter(this, riwayatTransaksiList)




        binding.rvPesanan.apply {
            layoutManager = LinearLayoutManager(this@RiwayatTransaksi)
            adapter = riwayatTransaksiAdapter
        }

        // Mengambil data dari database transaksi dengan filter properti "selesai" == true
        databaseReference.orderByChild("selesai").equalTo(true)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    riwayatTransaksiList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val transaksi = dataSnapshot.getValue(TransaksiModel::class.java)
                        transaksi?.let {
                            riwayatTransaksiList.add(it)
                        }
                    }
                    riwayatTransaksiAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
    }
}