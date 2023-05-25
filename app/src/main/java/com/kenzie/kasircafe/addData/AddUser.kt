package com.kenzie.kasircafe.addData

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kenzie.kasircafe.R
import com.kenzie.kasircafe.datakafe.dataMeja
import com.kenzie.kasircafe.datakafe.dataUser
import com.kenzie.kasircafe.model.EmailModel
import com.kenzie.kasircafe.model.MejaModel

class AddUser : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var dbRef : DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        // Initialize views
        emailEditText = findViewById(R.id.et_email)
        passwordEditText = findViewById(R.id.et_password)
        confirmPasswordEditText = findViewById(R.id.et_confirm_password)
        signUpButton = findViewById(R.id.btn_submit_user)


        // Initialize Firebase components
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("Email")


        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a user with email and password
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // User registration successful
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            val EmailId = dbRef.push().key!!
                            val email = EmailModel(EmailId,email)
                            // Save additional user information to the database

                            dbRef.child(EmailId).setValue(email)
                                .addOnCompleteListener{
                                    Toast.makeText(this,"Data Email Telah Ditambahkan",Toast.LENGTH_LONG).show()
                                    val intent = Intent(this, dataUser::class.java)
                                    startActivity(intent)
                                }
                        }
                    } else {
                        // User registration failed
                        Toast.makeText(
                            this,
                            "Menambahakan User Gagal: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}