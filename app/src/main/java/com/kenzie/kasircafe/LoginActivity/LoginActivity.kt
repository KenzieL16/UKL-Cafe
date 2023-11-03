package com.kenzie.kasircafe.LoginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.kenzie.kasircafe.MainActivity
import com.kenzie.kasircafe.R
import com.kenzie.kasircafe.userActivity.AdminActivity
import com.kenzie.kasircafe.userActivity.KasirActivity
import com.kenzie.kasircafe.userActivity.ManagerActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth = FirebaseAuth.getInstance()
        var et_email = findViewById(R.id.et_email) as EditText
        var et_password = findViewById(R.id.et_password) as EditText
        var btn_submit = findViewById(R.id.btn_submit) as Button
        val auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().reference.child("Email")

        btn_submit.setOnClickListener {
            val email = et_email.text.toString()
            val password = et_password.text.toString()


            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val userEmail = user?.email

                    if (userEmail != null) {
                        Log.d("LoginActivity", "Login berhasil. Email pengguna: $userEmail")
                        val databaseReference = FirebaseDatabase.getInstance().getReference("Email")
                        databaseReference.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    for (userSnapshot in snapshot.children) {
                                        val userRole = userSnapshot.child("role").getValue(String::class.java)
                                        if (userRole != null) {
                                            Log.d("LoginActivity", "Peran pengguna: $userRole")
                                            when (userRole) {
                                                "Admin" -> {
                                                    // Arahkan ke aktivitas admin
                                                    val adminIntent = Intent(
                                                        this@LoginActivity,
                                                        AdminActivity::class.java
                                                    )
                                                    startActivity(adminIntent)
                                                    finish()
                                                }
                                                "Manager" -> {
                                                    // Arahkan ke aktivitas manager
                                                    val managerIntent = Intent(
                                                        this@LoginActivity,
                                                        ManagerActivity::class.java
                                                    )
                                                    startActivity(managerIntent)
                                                    finish()
                                                }
                                                "Kasir" -> {
                                                    // Arahkan ke aktivitas kasir
                                                    val kasirIntent = Intent(
                                                        this@LoginActivity,
                                                        KasirActivity::class.java
                                                    )
                                                    startActivity(kasirIntent)
                                                    finish()
                                                }
                                                else -> {
                                                    // Peran tidak valid
                                                    Toast.makeText(
                                                        this@LoginActivity,
                                                        "Peran tidak valid",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                        } else {
                                            // Peran tidak ditemukan
                                            Log.d("LoginActivity", "Peran tidak ditemukan")
                                            Toast.makeText(
                                                this@LoginActivity,
                                                "Peran tidak ditemukan",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }else {
                                        // Pengguna tidak ditemukan dalam database
                                        Log.d("LoginActivity", "Pengguna tidak ditemukan dalam database")
                                        Toast.makeText(this@LoginActivity, "Pengguna tidak ditemukan", Toast.LENGTH_SHORT).show()
                                    }
                                }

                            override fun onCancelled(databaseError: DatabaseError) {
                                // Gagal mengambil data dari Firebase
                                Log.d("LoginActivity", "Gagal mengambil data dari Firebase")
                                Toast.makeText(this@LoginActivity, "Gagal mengambil data dari Firebase", Toast.LENGTH_SHORT).show()
                            }
                        })
                        }
                } else {
                    // Login gagal
                    Log.d("LoginActivity", "Login gagal")
                    Toast.makeText(this@LoginActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}





//private fun loginUser(email: String, password: String) {
//    firebaseAuth.signInWithEmailAndPassword(email, password)
//        .addOnCompleteListener(this) { task ->
//            if (task.isSuccessful) {
//                //Login successful
//                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            } else {
//                // Login failed
//                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
//            }
//        }
//}
//}

//"rules": {
//    ".read": "true",
//    ".write": "true"
//}
//}