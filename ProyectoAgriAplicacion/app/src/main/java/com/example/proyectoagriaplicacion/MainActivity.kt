package com.example.proyectoagriaplicacion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.proyectoagriaplicacion.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    private lateinit var binding: ActivityMainBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Iniciamos firebase auth
        auth = Firebase.auth

        val btnLogOut = findViewById<ImageButton>(R.id.btnLogout)

        btnLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(
                Intent(
                    applicationContext,
                    LoginActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            Toast.makeText(applicationContext, "Sesion cerrada", Toast.LENGTH_LONG).show()
        }


        val navigation = findViewById<View>(R.id.nav_view) as BottomNavigationView

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.fragmentMenu)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_market,R.id.navigation_map, R.id.navigation_history, R.id.navigation_profile
            )
        )


        navView.setupWithNavController(navController)
    }


}