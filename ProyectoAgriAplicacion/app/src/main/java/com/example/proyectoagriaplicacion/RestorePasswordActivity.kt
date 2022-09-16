package com.example.proyectoagriaplicacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RestorePasswordActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore_password)
        mAuth = FirebaseAuth.getInstance()

        findViewById<Button>(R.id.btnRestore).setOnClickListener{
            val email = findViewById<EditText>(R.id.email).text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Enter your email!", Toast.LENGTH_SHORT).show()
            } else {
                mAuth!!.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@RestorePasswordActivity, "Check email to reset your password!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@RestorePasswordActivity, "Fail to send reset password email!", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }


}