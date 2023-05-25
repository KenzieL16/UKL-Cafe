package com.kenzie.kasircafe.addData

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kenzie.kasircafe.R
import com.kenzie.kasircafe.datakafe.dataMeja
import com.kenzie.kasircafe.datakafe.dataMenu
import com.kenzie.kasircafe.model.MejaModel
import com.kenzie.kasircafe.model.MenuModel

class AddMenu : AppCompatActivity() {

    private lateinit var et_Menu : EditText
    private lateinit var et_Ket_Menu : EditText
    private lateinit var et_harga_Menu : EditText
    private lateinit var btn_savemenu : Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)

        et_Menu = findViewById(R.id.tambahnamamenu)
        et_harga_Menu = findViewById(R.id.hargamenu)
        et_Ket_Menu = findViewById(R.id.ketmenu)
        btn_savemenu = findViewById(R.id.submitmenu)

        dbRef = FirebaseDatabase.getInstance().getReference("Menu")

        btn_savemenu.setOnClickListener{
            savedatamenu()

        }
    }

    private fun savedatamenu() {
        val mejaNama = et_Menu.text.toString()
        val menuHarga = et_harga_Menu.text.toString()
        val menuKet = et_Ket_Menu.text.toString()

        if(mejaNama.isEmpty()){
            et_Menu.error = "Masukkan Data Meja"
        }
        if(menuKet.isEmpty()){
            et_Ket_Menu.error = "Masukkan Data Menu"
        }
        if(menuHarga.isEmpty()){
            et_harga_Menu.error = "Masukkan Data Menu"
        }

        val MenuId = dbRef.push().key!!
        val menu = MenuModel(MenuId,mejaNama,menuHarga,menuKet)


        dbRef.child(MenuId).setValue(menu)
            .addOnCompleteListener{
                Toast.makeText(this,"Data Menu Telah Ditambahkan", Toast.LENGTH_LONG).show()
                val intent = Intent(this, dataMenu::class.java)
                startActivity(intent)
            }.addOnFailureListener {err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}