package com.kenzie.kasircafe.datakafe

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.db.williamchart.view.BarChartView
import com.db.williamchart.view.HorizontalBarChartView
import com.google.firebase.database.*
import com.kenzie.kasircafe.R




class DataChart : AppCompatActivity() {

    private lateinit var barChart: BarChartView
    private lateinit var horizontalBarChart: HorizontalBarChartView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_chart)

//        barChart = findViewById(R.id.verticalbarchart)
        horizontalBarChart = findViewById(R.id.Horizontal_barChart)
        // Mengambil referensi ke tabel transaksi
        val database= FirebaseDatabase.getInstance()
        val transaksiRef= database.getReference("transaksi")


        transaksiRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val menuCounts = mutableMapOf<String, Int>()
                // Loop melalui semua transaksi
                for (transaksiSnapshot in snapshot.children) {
                    val menu = transaksiSnapshot.child("menu").getValue(String::class.java)
                    // Tambahkan jumlah pembelian untuk menu tertentu
                    if (menu != null) {
                        if (menuCounts.containsKey(menu)) {
                            menuCounts[menu] = menuCounts[menu]!! + 1
                        } else {
                            menuCounts[menu] = 1
                        }
                    }
                }


                val entries = mutableListOf<Pair<String, Float>>()
                for ((menu, count) in menuCounts) {
                    entries.add(menu to count.toFloat())
                }
                horizontalBarChart.animate(entries)
//                horizontalBarChartView.animate(entries)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Penanganan kesalahan jika ada
            }
        })
    }
}

