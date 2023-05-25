package com.kenzie.kasircafe.userActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.kenzie.kasircafe.R
import com.kenzie.kasircafe.datakafe.dataMeja
import com.kenzie.kasircafe.datakafe.dataMenu
import com.kenzie.kasircafe.datakafe.dataUser

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        var btn_meja = findViewById(R.id.Meja) as Button
        var btn_menu = findViewById(R.id.Menu) as Button
        var btn_user = findViewById(R.id.User) as Button

        btn_meja.setOnClickListener {
            val intent = Intent(this, dataMeja::class.java)
            startActivity(intent)
        }

        btn_menu.setOnClickListener {
            val intent = Intent(this, dataMenu::class.java)
            startActivity(intent)
        }

        btn_user.setOnClickListener {
            val intent = Intent(this, dataUser::class.java)
            startActivity(intent)
        }
    }
}