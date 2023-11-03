package com.kenzie.kasircafe.userActivity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.kenzie.kasircafe.R




class KasirActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kasir)
        var btn1 = findViewById(R.id.button1) as Button
        var btn2 = findViewById(R.id.button2) as Button

        btn1.setOnClickListener {
            val intent = Intent(this, RiwayatTransaksi::class.java)
            startActivity(intent)
        }

        btn2.setOnClickListener {
            val intent = Intent(this, StatusPembayaran::class.java)
            startActivity(intent)
        }

    }
}