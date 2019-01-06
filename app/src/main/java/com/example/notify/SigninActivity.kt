package com.example.notify

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signin.*


class MainActivity : AppCompatActivity() {

    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        if (mAuth.currentUser != null) {
            logIn()
        }
    }
    fun goClicked (view: View) {
            // Check if we can login the user
            mAuth.signInWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString())
                .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    logIn()
                } else {
                    Toast.makeText(this, " Login Failed. Try Again.", Toast.LENGTH_LONG).show()

                    /*mAuth.createUserWithEmailAndPassword(emailEditText?.text.toString(),passwordEditText?.text.toString())
                        .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            FirebaseDatabase.getInstance().getReference().child("users").child(task.result!!.user!!.uid)
                                    .child("email").setValue(emailEditText?.text.toString())

                            logIn()
                        }else{
                            Toast.makeText(this, " Login Failed. Try Again.", Toast.LENGTH_LONG).show()
                                }
                            }*/
                            }
                        }
                    }
    fun goToSignup (view: View) {
        val intent = Intent(this, SignupActivity::class.java)
        intent.putExtra("email", emailEditText?.text.toString())
        startActivity(intent)
    }
    fun logIn() {
                // Move to next activity

                val intent = Intent(this, SnapsActivity::class.java)
                startActivity(intent)

            }
        }

/*
TODO
    Photo+Name+Message display at "Snap Activity"*/
