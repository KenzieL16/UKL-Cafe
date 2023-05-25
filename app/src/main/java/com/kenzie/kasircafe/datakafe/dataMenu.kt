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
import com.kenzie.kasircafe.adapter.menuAdapter
import com.kenzie.kasircafe.addData.AddMenu
import com.kenzie.kasircafe.detail.DetailMenu
import com.kenzie.kasircafe.model.MenuModel

class dataMenu : AppCompatActivity() {

    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var rvLoadingData: TextView
    private lateinit var listMenu:ArrayList<MenuModel>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_menu)
        menuRecyclerView = findViewById(R.id.tvNamaMenu)
        menuRecyclerView.layoutManager = LinearLayoutManager(this)
        menuRecyclerView.setHasFixedSize(true)
        rvLoadingData = findViewById(R.id.tv_loading_data)

        listMenu = arrayListOf<MenuModel>()
        getMenuData()

        var btn_tambah_menu : FloatingActionButton = findViewById(R.id.tambahMenu)

        btn_tambah_menu.setOnClickListener {
            val intent = Intent(this, AddMenu::class.java)
            startActivity(intent)
        }
    }

    private fun getMenuData() {
        menuRecyclerView.visibility = View.GONE
        rvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Menu")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listMenu.clear()
                if (snapshot.exists()) {
                    for (menuSnap in snapshot.children){
                        val menuData = menuSnap.getValue(MenuModel::class.java)
                        listMenu.add(menuData!!)
                    }
                    val mAdapter = menuAdapter(listMenu)
                    menuRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : menuAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@dataMenu, DetailMenu::class.java)

                            intent.putExtra("menuId", listMenu[position].menuId)
                            intent.putExtra("menuNama", listMenu[position].menuNama)
                            intent.putExtra("menuHarga", listMenu[position].menuHarga)
                            intent.putExtra("menuKet", listMenu[position].menuKet)
                            startActivity(intent)
                        }

                    })

                    menuRecyclerView.visibility = View.VISIBLE
                    rvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}
