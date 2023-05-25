package com.kenzie.kasircafe.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kenzie.kasircafe.R
import com.kenzie.kasircafe.datakafe.dataMeja
import com.kenzie.kasircafe.model.MejaModel


class DetailMeja : AppCompatActivity() {

    private lateinit var tvMejaId :TextView
    private lateinit var tvNamaMeja : TextView
    private lateinit var tvKetMeja : TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_meja)

        initview()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("mejaId").toString(),
                intent.getStringExtra("mejaNama").toString(),
                intent.getStringExtra("mejaKet").toString()
            )
        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("mejaId").toString(),
            )
        }
    }
    private fun deleteRecord(
        id: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Meja").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Data Meja Telah Dihapus", Toast.LENGTH_LONG).show()
            val intent = Intent(this, dataMeja::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Hapus Data Gagal ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
    private fun initview() {
        tvMejaId = findViewById(R.id.tvMejaId)
        tvNamaMeja = findViewById(R.id.tvMejaName2)
        tvKetMeja = findViewById(R.id.tvMejaDesc2)
        btnUpdate = findViewById(R.id.btnUpdateMeja)
        btnDelete = findViewById(R.id.btnDeleteMeja)
    }
    private fun setValuesToViews(){

        tvMejaId.text = intent.getStringExtra("mejaId")
        tvNamaMeja.text = intent.getStringExtra("mejaNama")
        tvKetMeja.text = intent.getStringExtra("mejaKet")
    }
    private fun openUpdateDialog(
        mejaId: String,
        mejaNama: String,
        mejaKet:String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_updatemeja,null)

        mDialog.setView(mDialogView)

        val etNamaMeja = mDialogView.findViewById<EditText>(R.id.tambahnomormeja)
        val etKetMeja = mDialogView.findViewById<EditText>(R.id.ketmeja)
        val btnUpdateMeja = mDialogView.findViewById<Button>(R.id.btnUpdateMeja)

        etNamaMeja.setText(intent.getStringExtra("mejaNama").toString())
        etKetMeja.setText(intent.getStringExtra("mejaKet").toString())

        mDialog.setTitle("Update $mejaNama Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateMeja.setOnClickListener{
            updatedatameja(
                mejaId,
                etNamaMeja.text.toString(),
                etKetMeja.text.toString()
            )
            Toast.makeText(applicationContext,"Data Meja Telah Diubah", Toast.LENGTH_LONG).show()

            tvNamaMeja.text = etNamaMeja.text.toString()
            tvKetMeja.text = etKetMeja.text.toString()

            alertDialog.dismiss()
        }

    }
    private fun updatedatameja(
        id:String,
        name:String,
        ket:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Meja").child(id)
        val mejaInfo = MejaModel(id,name,ket)
        dbRef.setValue(mejaInfo)
    }
}