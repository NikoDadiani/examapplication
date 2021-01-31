package com.example.myapp15auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var resetButton: Button
    private lateinit var resetEmailEditText: EditText

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        auth = FirebaseAuth.getInstance()

        resetButton = findViewById(R.id.resetSendButton)
        resetEmailEditText = findViewById(R.id.resetEmailEditText)

        resetButton.setOnClickListener {
            val email = resetEmailEditText.text.toString()
            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText( this, "Check Email", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText( this, "Error", Toast.LENGTH_SHORT).show()
                    
                }
            }
        }


    }
}