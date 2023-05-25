package com.kenzie.kasircafe.detail

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import com.kenzie.kasircafe.R
import com.kenzie.kasircafe.datakafe.dataMeja
import com.kenzie.kasircafe.datakafe.dataMenu
import com.kenzie.kasircafe.model.MejaModel
import com.kenzie.kasircafe.model.MenuModel

class DetailMenu : AppCompatActivity() {

    private lateinit var tvMenuId : TextView
    private lateinit var tvNamaMenu : TextView
    private lateinit var tvhargaMenu : TextView
    private lateinit var tvKetMenu : TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)

        initview()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("menuId").toString(),
                intent.getStringExtra("menuNama").toString(),
                intent.getStringExtra("menuHarga").toString(),
                intent.getStringExtra("mejaKet").toString()
            )
        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("menuId").toString(),
            )
        }
    }
    private fun deleteRecord(
        id: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Menu").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Data Menu Telah Dihapus", Toast.LENGTH_LONG).show()
            val intent = Intent(this, dataMenu::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Hapus Data Gagal ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
    private fun initview() {
        tvMenuId = findViewById(R.id.tvMenuId)
        tvNamaMenu = findViewById(R.id.tvMenuName2)
        tvKetMenu = findViewById(R.id.tvMenuDesc2)
        tvhargaMenu = findViewById(R.id.tvMenuharga)
        btnUpdate = findViewById(R.id.btnUpdateMenu)
        btnDelete = findViewById(R.id.btnDeleteMenu)
    }
    private fun setValuesToViews(){

        tvMenuId.text = intent.getStringExtra("menuId")
        tvNamaMenu.text = intent.getStringExtra("menuNama")
        tvhargaMenu.text = intent.getStringExtra("menuHarga")
        tvKetMenu.text = intent.getStringExtra("menuKet")
    }

    private fun openUpdateDialog(
        menuId: String,
        menuNama: String,
        menuHarga: String,
        menuKet:String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_updatemenu,null)

        mDialog.setView(mDialogView)

        val etNamaMenu = mDialogView.findViewById<EditText>(R.id.tambahnamamenu)
        val ethargaMenu = mDialogView.findViewById<EditText>(R.id.hargamenu)
        val etKetMenu = mDialogView.findViewById<EditText>(R.id.ketmenu)
        val btnUpdateMenu = mDialogView.findViewById<Button>(R.id.btnUpdateMenu)

        etNamaMenu.setText(intent.getStringExtra("menuNama").toString())
        ethargaMenu.setText(intent.getStringExtra("menuHarga").toString())
        etKetMenu.setText(intent.getStringExtra("menuKet").toString())

        mDialog.setTitle("Update $menuNama Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateMenu.setOnClickListener{
            updatedatamenu(
                menuId,
                etNamaMenu.text.toString(),
                ethargaMenu.text.toString(),
                etKetMenu.text.toString()
            )
            Toast.makeText(applicationContext,"Data Menu Telah Diubah", Toast.LENGTH_LONG).show()

            tvNamaMenu.text = etNamaMenu.text.toString()
            tvhargaMenu.text = ethargaMenu.text.toString()
            tvKetMenu.text = etKetMenu.text.toString()

            alertDialog.dismiss()
        }

    }
    private fun updatedatamenu(
        id:String,
        name:String,
        harga:String,
        ket:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Menu").child(id)
        val menuInfo = MenuModel(id,name,harga,ket)
        dbRef.setValue(menuInfo)
    }
}