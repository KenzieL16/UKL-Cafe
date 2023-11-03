package com.kenzie.kasircafe.addData

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.kenzie.kasircafe.R
import com.kenzie.kasircafe.datakafe.dataMeja
import com.kenzie.kasircafe.model.TransaksiModel
import com.kenzie.kasircafe.userActivity.KasirActivity
import java.text.SimpleDateFormat
import java.util.*


class AddTransaksi : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var menuRef: DatabaseReference
    private lateinit var mejaRef: DatabaseReference
    private lateinit var spinnerMenu: Spinner
    private lateinit var spinnerPayment: Spinner
    private lateinit var spinnerMeja: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaksi)

        database = FirebaseDatabase.getInstance()
        menuRef = database.getReference("Menu")
        mejaRef = database.getReference("Meja")
        spinnerPayment = findViewById(R.id.spinner_payment)
        spinnerMenu = findViewById(R.id.spinner_menu)
        spinnerMeja = findViewById(R.id.spinner_meja)

        setupSpinnerMenu()
        setupSpinnerMeja()

        val btnAddTransaksi = findViewById<Button>(R.id.addTransaksi)
        btnAddTransaksi.setOnClickListener {
            tambahTransaksi()
        }
    }

    private fun tambahTransaksi() {
        val idTransaksi = UUID.randomUUID().toString()
        val menu = spinnerMenu.selectedItem.toString()
        val selectedPayment = spinnerPayment.selectedItem.toString()
        val selectedMeja = spinnerMeja.selectedItem.toString()
        val tanggal = getCurrentDate()

        val hargaMenuRef = menuRef.orderByChild("menuNama").equalTo(menu)
        hargaMenuRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val menuPrice = snapshot.children.first().child("menuHarga").getValue(String::class.java)
                    menuPrice?.let {
                        val transaction = TransaksiModel(idTransaksi, menu, tanggal, selectedPayment, selectedMeja, it)
                        val transactionRef = database.getReference("transaksi").child(idTransaksi)

                        transactionRef.setValue(transaction)
                            .addOnSuccessListener {
                                Toast.makeText(this@AddTransaksi, "Transaksi ditambahkan", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this@AddTransaksi, "Transaksi Gagal", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    Toast.makeText(this@AddTransaksi, "Menu tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }


    private fun setupSpinnerMeja() {
        val placeholder = "Pilih Meja"
        val listMeja = mutableListOf(placeholder)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listMeja)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMeja.adapter = adapter

        mejaRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listMeja.clear()
                listMeja.add(placeholder)
                for (mejaSnapshot in snapshot.children) {
                    val mejaName = mejaSnapshot.child("mejaNama").getValue(String::class.java)
                    val mejaId = mejaSnapshot.child("mejaId").getValue(String::class.java)

                    // Mengambil status transaksi dari Firebase
                    val transaksiRef = database.getReference("transaksi")
                        .orderByChild("selectedMeja")
                        .equalTo(mejaName)

                    transaksiRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(transaksiSnapshot: DataSnapshot) {
                            var isMejaAvailable = true
                            for (transaksiDataSnapshot in transaksiSnapshot.children) {
                                val transaksi = transaksiDataSnapshot.getValue(TransaksiModel::class.java)
                                // Memeriksa apakah status transaksi sudah selesai atau belum
                                if (transaksi != null && !transaksi.isSelesai) {
                                    isMejaAvailable = false
                                    break
                                }
                            }
                            // Jika meja tersedia (status transaksi selesai), tambahkan ke daftar pilihan
                            if (isMejaAvailable) {
                                mejaName?.let { listMeja.add(it) }
                            }
                            adapter.notifyDataSetChanged()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Handle error
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }


    private fun setupSpinnerMenu() {
        val placeholder = "Pilih Menu"
        val listMenu = mutableListOf(placeholder)

        val paymentMethods = listOf("Cash", "Debit", "QRIS")
        val paymentAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paymentMethods)
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPayment.adapter = paymentAdapter

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listMenu)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMenu.adapter = adapter

        menuRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listMenu.clear()
                listMenu.add(placeholder)
                for (menuSnapshot in snapshot.children) {
                    val menuName = menuSnapshot.child("menuNama").getValue(String::class.java)
                    menuName?.let { listMenu.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}
