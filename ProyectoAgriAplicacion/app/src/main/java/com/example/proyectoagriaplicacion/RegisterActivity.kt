package com.example.proyectoagriaplicacion

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoagriaplicacion.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {


    private lateinit var nameUser: EditText
    private lateinit var surnameUser: EditText
    private lateinit var phoneUser: EditText
    private lateinit var emailUser: EditText
    private lateinit var passwordUser: EditText
    private lateinit var departmentUser: Spinner
    private lateinit var databaseR: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        nameUser = findViewById(R.id.nameUser)
        surnameUser = findViewById(R.id.surnameUser)
        phoneUser = findViewById(R.id.phoneUser)
        emailUser = findViewById(R.id.emailUser)
        passwordUser = findViewById(R.id.passwordUser)
        departmentUser = findViewById<View>(R.id.departmentUser) as Spinner
        databaseR=database.reference.child("users")
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            register(
                emailUser.text.toString(),
                passwordUser.text.toString(),
                nameUser.text.toString(),
                surnameUser.text.toString(),
                phoneUser.text.toString(),
                departmentUser.textDirection.toString()
            )

        }
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.departamentos, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        departmentUser.adapter = adapter

    }

    private fun register(
        emailUser: String,
        passwordUser: String,
        nameUser: String,
        surnameUser: String,
        phoneUser: String,
        departmentUser: String
    ) {
        firebaseAuth!!.createUserWithEmailAndPassword(emailUser, passwordUser)
            .addOnCompleteListener (this){ task ->

                if(!TextUtils.isEmpty(emailUser) && !TextUtils.isEmpty(passwordUser) && !TextUtils.isEmpty(surnameUser)&& !TextUtils.isEmpty(phoneUser)&& !TextUtils.isEmpty(departmentUser) && !TextUtils.isEmpty(nameUser)){

                    if(task.isComplete) {

                        val user: FirebaseUser?=firebaseAuth.currentUser
                        val userDB = databaseR.child(user?.uid!!)
                        userDB.child("nombres").setValue(nameUser)
                        userDB.child("apellidos").setValue(surnameUser)
                        userDB.child("email").setValue(emailUser)
                        userDB.child("departamento").setValue(departmentUser)
                        userDB.child("telefono").setValue(phoneUser)
                        userDB.child("contrasena").setValue(passwordUser)

                        Log.d("TAG", "createUserWithEmail:success")

                    }
                }
                else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }

    }
}







