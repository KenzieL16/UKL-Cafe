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
import com.kenzie.kasircafe.model.MejaModel

class AddMeja : AppCompatActivity() {

    private lateinit var et_Meja : EditText
    private lateinit var et_Ket_Meja : EditText
    private lateinit var btn_savemeja : Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meja)

        et_Meja = findViewById(R.id.tambahnomormeja)
        et_Ket_Meja = findViewById(R.id.ketmeja)
        btn_savemeja = findViewById(R.id.submitmeja)

        dbRef = FirebaseDatabase.getInstance().getReference("Meja")

        btn_savemeja.setOnClickListener{
            savedatameja()

        }
    }

    private fun savedatameja() {
        val mejaNama = et_Meja.text.toString()
        val mejaKet = et_Ket_Meja.text.toString()

        if(mejaNama.isEmpty()){
            et_Meja.error = "Masukkan Data Meja"
        }
        if(mejaKet.isEmpty()){
            et_Ket_Meja.error = "Masukkan Data Meja"
        }

        val MejaId = dbRef.push().key!!
        val meja = MejaModel(MejaId,mejaNama,mejaKet)


        dbRef.child(MejaId).setValue(meja)
            .addOnCompleteListener{
                Toast.makeText(this,"Data Meja Telah Ditambahkan",Toast.LENGTH_LONG).show()
                val intent = Intent(this, dataMeja::class.java)
                startActivity(intent)
            }.addOnFailureListener {err ->
                Toast.makeText(this, "Error ${err.message}",Toast.LENGTH_LONG).show()
            }
    }
}