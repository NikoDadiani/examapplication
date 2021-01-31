package com.example.myapp15auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class personActivity : AppCompatActivity() {
    private lateinit var userInfoTextView: TextView
    private lateinit var changePasswordButton: Button
    private lateinit var logoutButton: Button
    private lateinit var inputName: EditText
    private lateinit var imageUrlInput: EditText
    private lateinit var saveButton: Button
    private lateinit var nameTextView: TextView
    private lateinit var imageView: ImageView

    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person)

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("UserInfo")

        userInfoTextView = findViewById(R.id.userInfoTextView)
        changePasswordButton = findViewById(R.id.passwordChangeButton)
        logoutButton = findViewById(R.id.logOutButton)
        inputName = findViewById(R.id.nameEditText)
        imageUrlInput = findViewById(R.id.imageUrlEditText)
        saveButton = findViewById(R.id.saveButton)
        nameTextView = findViewById(R.id.nameTextView)
        imageView = findViewById(R.id.imageView)

        userInfoTextView.text = auth.currentUser?.uid

        logoutButton.setOnClickListener() {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        changePasswordButton.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))


        }

        saveButton.setOnClickListener {
            val name = inputName.text.toString()
            val url = imageUrlInput.text.toString()

            val  personInfo = PersonInfo(name, url)

            if (auth.currentUser?.uid != null) {

                db.child(auth.currentUser?.uid!!).setValue(personInfo).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText( this, "Success!", Toast.LENGTH_SHORT).show()
                        inputName.text = null
                        imageUrlInput.text = null
                    } else {
                        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                    }

                }
            }

        }

        db.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (auth.currentUser?.uid != null) {

                    val personInfo: PersonInfo? = snapshot.child(auth.currentUser?.uid!!).getValue(PersonInfo::class.java)

                    if (personInfo !=null) {
                        nameTextView.text=personInfo.name

                        Glide.with(this@personActivity)
                                .load(personInfo.profileImageUrl)
                                .centerCrop()
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .into(imageView)





                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@personActivity, "Error!", Toast.LENGTH_SHORT).show()
            }


        })



    }

}

class PersonActivity {

}
