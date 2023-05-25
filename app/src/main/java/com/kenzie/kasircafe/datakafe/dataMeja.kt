package com.kenzie.kasircafe.datakafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.kenzie.kasircafe.R
import com.kenzie.kasircafe.adapter.mejaAdapter
import com.kenzie.kasircafe.addData.AddMeja
import com.kenzie.kasircafe.detail.DetailMeja
import com.kenzie.kasircafe.model.MejaModel

class dataMeja : AppCompatActivity() {

    private lateinit var mejaRecyclerView: RecyclerView
    private lateinit var rvLoadingData: TextView
    private lateinit var listMeja:ArrayList<MejaModel>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_meja)

        mejaRecyclerView = findViewById(R.id.rvMeja)
        mejaRecyclerView.layoutManager = LinearLayoutManager(this)
        mejaRecyclerView.setHasFixedSize(true)
        rvLoadingData = findViewById(R.id.tv_loading_data)

        listMeja = arrayListOf<MejaModel>()
        getMejaData()

        var btn_tambah_meja : FloatingActionButton = findViewById(R.id.tambahMeja)

        btn_tambah_meja.setOnClickListener {
            val intent = Intent(this,AddMeja::class.java)
            startActivity(intent)
        }
    }

    private fun getMejaData() {
        mejaRecyclerView.visibility = View.GONE
        rvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Meja")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listMeja.clear()
                if (snapshot.exists()) {
                    for (mejaSnap in snapshot.children){
                        val mejaData = mejaSnap.getValue(MejaModel::class.java)
                        listMeja.add(mejaData!!)
                    }
                    val mAdapter = mejaAdapter(listMeja)
                    mejaRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : mejaAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@dataMeja, DetailMeja::class.java)

                            intent.putExtra("mejaId", listMeja[position].mejaId)
                            intent.putExtra("mejaNama", listMeja[position].mejaNama)
                            intent.putExtra("mejaKet", listMeja[position].mejaKet)
                            startActivity(intent)
                        }

                    })

                    mejaRecyclerView.visibility = View.VISIBLE
                    rvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}