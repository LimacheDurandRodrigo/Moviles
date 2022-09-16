package com.example.proyectoagriaplicacion


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val firebaseUser = FirebaseAuth.getInstance().currentUser

        if (firebaseUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        auth = FirebaseAuth.getInstance()
        val etEmail = findViewById<TextInputLayout>(R.id.etEmail)
        val etPassword = findViewById<TextInputLayout>(R.id.etPassword)

        val btnLogIn = findViewById<Button>(R.id.btnLogIn)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        val tvRestorePassword = findViewById<TextView>(R.id.tvRestorePassword)

        btnLogIn.setOnClickListener {
            val txt_email = etEmail.editText!!.text.toString()
            val txt_password = etPassword.editText!!.text.toString()
            if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                Toast.makeText(this@LoginActivity, "Faltan campos", Toast.LENGTH_SHORT).show()
            } else {
                auth!!.signInWithEmailAndPassword(txt_email, txt_password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Error al autentificar",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
            }
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(application, RegisterActivity::class.java)
            startActivity(intent)
        }

        tvRestorePassword.setOnClickListener {
            val intent = Intent(application, RestorePasswordActivity::class.java)
            startActivity(intent)

        }
    }







}
