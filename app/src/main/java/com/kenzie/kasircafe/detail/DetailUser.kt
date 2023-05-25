package com.kenzie.kasircafe.detail


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.kenzie.kasircafe.R


class DetailUser : AppCompatActivity() {

    private lateinit var tvEmailId : TextView
    private lateinit var tvEmail : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        initview()
        setValuesToViews()

    }

    private fun initview() {
        tvEmail = findViewById(R.id.rvEmail)
        tvEmailId = findViewById(R.id.tvEmailId)


    }
    private fun setValuesToViews(){

        tvEmailId.text = intent.getStringExtra("emailId")
        tvEmail.text = intent.getStringExtra("email")

    }
}