package com.example.notify

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.widget.EditText
import com.example.notify.R.id.passwordEditText2


class SignupActivity : AppCompatActivity() {

    var fNameEditText: EditText? = null
    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    var passwordEditText2: EditText? = null
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        fNameEditText = findViewById(R.id.txtFullName)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        passwordEditText2 = findViewById(R.id.passwordEditText2)


        val email = intent.getStringExtra("email")
        emailEditText?.setText(email)
    }

    fun signupClicked (view: View){

        if(passwordEditText?.text.toString() == passwordEditText2?.text.toString()) {

            mAuth.createUserWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val userMap:Map<String, String> =
                                    mapOf("full-Name" to fNameEditText?.text.toString(),
                                          "e-mail" to emailEditText?.text.toString(),
                                          "location" to "")

                            FirebaseDatabase.getInstance().getReference()
                                    .child("users").child(task.result!!.user!!.uid)
                                    .setValue(userMap)

                            Toast.makeText(this, " User created successfully.", Toast.LENGTH_LONG).show()

                            val intent = Intent(this, SnapsActivity::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, " Login Failed. Try Again.", Toast.LENGTH_LONG).show()
                        }
                    }
        }
        else
        {
            Toast.makeText(this, " Password & Repeat Password do not match.", Toast.LENGTH_LONG).show()
        }
    }
}