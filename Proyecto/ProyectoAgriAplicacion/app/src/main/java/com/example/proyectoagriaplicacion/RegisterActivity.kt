package com.example.proyectoagriaplicacion

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class RegisterActivity : AppCompatActivity() {


    var auth: FirebaseAuth? = null

    var reference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()


        val emailUser: EditText = findViewById(R.id.emailUser)
        val passwordUser: EditText = findViewById(R.id.passwordUser)
        val departmentUser = findViewById<View>(R.id.departmentUser) as Spinner
        val btnRegister = findViewById<Button>(R.id.btnRegister)


        btnRegister.setOnClickListener {
                register(emailUser.getText().toString(),passwordUser.getText().toString())
        }

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.departamentos, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        departmentUser.adapter = adapter

    }
    private fun register(emailUser: String, passwordUser: String) {
        auth!!.createUserWithEmailAndPassword(emailUser, passwordUser)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth!!.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }


}

