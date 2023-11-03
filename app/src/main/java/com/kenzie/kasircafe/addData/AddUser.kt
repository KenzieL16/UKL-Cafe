package com.kenzie.kasircafe.addData

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kenzie.kasircafe.R
import com.kenzie.kasircafe.datakafe.dataUser
import com.kenzie.kasircafe.model.EmailModel

class AddUser : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var dbRef : DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var roleSpinner: Spinner

//    private val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        // Initialize views
        emailEditText = findViewById(R.id.et_email)
        passwordEditText = findViewById(R.id.et_password)
        signUpButton = findViewById(R.id.btn_submit_user)
        roleSpinner = findViewById(R.id.RoleSpinner)
//        val selectedRoleTextView = findViewById<TextView>(R.id.selectedRoleTextView) // Reference to the TextView

        // Initialize Firebase components
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("Email")


        val roles = arrayOf("Admin", "Kasir", "Manager") // Add a default "Select Role" option
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roleSpinner.adapter = adapter

        roleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                // Handle role selection here
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Do nothing
            }
        }


        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Create a user with email and password
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val uid = user?.uid ?: ""
                        val selectedRole = roleSpinner.selectedItem.toString()
                        val EmailId = dbRef.push().key!!
                        val role = roleSpinner.selectedItem.toString()
                        val email = EmailModel(EmailId,email,role)

                        dbRef.child(EmailId).setValue(email)
                                .addOnCompleteListener{
                                    Toast.makeText(this,"Data Email Telah Ditambahkan",Toast.LENGTH_LONG).show()
                                    val intent = Intent(this, dataUser::class.java)
                                    startActivity(intent)
                                }
                        // Store user role in the Realtime Database
//                        val database = FirebaseDatabase.getInstance()
//                        val userRoleRef = database.getReference("Email").child(uid).child("role")
//                        userRoleRef.setValue(selectedRole)
                    } else {
                        // Registration failed
                        Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                    }
//

                }
        }
    }
}