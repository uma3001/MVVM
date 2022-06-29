package com.example.mvvm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mvvm.ui.NewsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var etmailid: EditText
    private lateinit var etpassword: EditText
    private lateinit var btnsubmit: Button
    lateinit var Preferences: SharedPreferences
    var EMAIL = "Email"
    var PASSWORD = "Password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Preferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)

        etmailid = findViewById(R.id.etmailid)
        etpassword = findViewById(R.id.etpassword)
        btnsubmit = findViewById(R.id.btnsubmit)

        btnsubmit.setOnClickListener {
            when {
                etmailid.text.isNullOrEmpty() -> {
                    Toast.makeText(
                        this, "Please Enter a Valid Email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                etpassword.text.isNullOrEmpty() -> {
                    Toast.makeText(
                        this, "Please Enter the Correct Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = etmailid.text.toString()
                    val password: String = etpassword.text.toString()
                    val editor: SharedPreferences.Editor = Preferences.edit()

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            { task ->

                                if (task.isSuccessful)
                                {
                                    editor.putString(EMAIL, etmailid.getText().toString())
                                    editor.putString(PASSWORD, etpassword.getText().toString())
                                    editor.commit()

                                    val FireBase: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this, "Welcome $email", Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    val intent = Intent(this, NewsActivity::class.java)
                                    startActivity(intent)
                                }
                                else
                                {
                                    Toast.makeText(
                                        this, "Please enter valid e-mail id and password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                }
            }
        }
    }
}