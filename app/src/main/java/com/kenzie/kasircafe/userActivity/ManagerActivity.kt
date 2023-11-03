package com.kenzie.kasircafe.userActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.kenzie.kasircafe.R
import com.kenzie.kasircafe.datakafe.DataChart

class ManagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)
        var btn1 = findViewById(R.id.button1) as Button
        var btn2 = findViewById(R.id.button2) as Button

        btn1.setOnClickListener {
            val intent = Intent(this, RiwayatTransaksi::class.java)
            startActivity(intent)
        }

        btn2.setOnClickListener {
            val intent = Intent(this, DataChart::class.java)
            startActivity(intent)
        }
    }
}