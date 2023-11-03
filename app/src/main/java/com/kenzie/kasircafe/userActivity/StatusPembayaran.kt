package com.kenzie.kasircafe.userActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.kenzie.kasircafe.R
import com.kenzie.kasircafe.adapter.StatusAdapter
import com.kenzie.kasircafe.adapter.pesananAdapter
import com.kenzie.kasircafe.addData.AddTransaksi
import com.kenzie.kasircafe.databinding.ActivityStatusPembayaranBinding
import com.kenzie.kasircafe.model.TransaksiModel

class StatusPembayaran : AppCompatActivity() {
    private lateinit var binding: ActivityStatusPembayaranBinding
    private lateinit var databaseReference: DatabaseReference
    private val transaksiList = mutableListOf<TransaksiModel>()
    private lateinit var transaksiAdapter: StatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatusPembayaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("transaksi")
        transaksiAdapter = StatusAdapter(this, transaksiList)
        var btn_tambah_transaksi : FloatingActionButton = findViewById(R.id.tambahPesanan)

        btn_tambah_transaksi.setOnClickListener {
            val intent = Intent(this, AddTransaksi::class.java)
            startActivity(intent)
        }


        binding.rvTransaksi.apply {
            layoutManager = LinearLayoutManager(this@StatusPembayaran)
            adapter = transaksiAdapter
        }

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                transaksiList.clear()
                for (dataSnapshot in snapshot.children) {
                    val transaksi = dataSnapshot.getValue(TransaksiModel::class.java)
                    transaksi?.let {
                        if (!it.isSelesai) {
                            transaksiList.add(it)
                        }
                    }
                }
                transaksiAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}