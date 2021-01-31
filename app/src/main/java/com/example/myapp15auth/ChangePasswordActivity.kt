package com.example.myapp15auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var newPasswordEditText: EditText
    private lateinit var submitButton: Button

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        auth = FirebaseAuth.getInstance()

        newPasswordEditText = findViewById(R.id.newPasswordEditText)
        submitButton = findViewById(R.id.newPasswordSubmit)

        submitButton.setOnClickListener {
            val newPassword  =newPasswordEditText.text.toString()
            auth.currentUser?.updatePassword(newPassword)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText( this, "success" , Toast.LENGTH_SHORT).show()
                    
                } else {
                    Toast.makeText( this, "Error", Toast.LENGTH_SHORT).show()


                }




            }




        }


    }
}