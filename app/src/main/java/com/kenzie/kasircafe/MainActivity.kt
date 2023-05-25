package com.kenzie.kasircafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.kenzie.kasircafe.userActivity.AdminActivity
import com.kenzie.kasircafe.userActivity.KasirActivity
import com.kenzie.kasircafe.userActivity.ManagerActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn_kasir = findViewById(R.id.button1) as Button
        var btn_admin = findViewById(R.id.button2) as Button
        var btn_manager = findViewById(R.id.button3) as Button

        btn_kasir.setOnClickListener {
            val intent = Intent(this, KasirActivity::class.java)
            startActivity(intent)
        }

        btn_admin.setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }

        btn_manager.setOnClickListener {
            val intent = Intent(this, ManagerActivity::class.java)
            startActivity(intent)
        }
    }
}