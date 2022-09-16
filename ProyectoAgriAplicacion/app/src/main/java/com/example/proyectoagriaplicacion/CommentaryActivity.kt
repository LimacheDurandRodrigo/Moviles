package com.example.proyectoagriaplicacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import com.example.proyectoagriaplicacion.model.Commentary
import com.example.proyectoagriaplicacion.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class CommentaryActivity : AppCompatActivity() {
    var comentariy: EditText? = null
    var starsCommentary: RatingBar? = null
    var btnAddCommentary: Button? = null
    var idPublicador: String? = null
    var mDatabase: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commentary)
        mDatabase = FirebaseDatabase.getInstance().reference
        idPublicador = intent.extras!!.getString("idPublicador")
        comentariy = findViewById(R.id.txtComentary)
        starsCommentary = findViewById(R.id.starsCommentary)
        btnAddCommentary = findViewById(R.id.btnAddCommentary)
        var btnAddCommentary = findViewById<Button>(R.id.btnAddCommentary)

        btnAddCommentary.setOnClickListener(View.OnClickListener { uploadFirebase() })
    }

    private fun uploadFirebase() {

        val intStars = starsCommentary!!.rating.toInt()
        val stars = intStars.toString()
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val fuser = FirebaseAuth.getInstance().currentUser
        val commentary = Commentary()
        commentary.idCommentary = UUID.randomUUID().toString()

        commentary.stars = stars
        if (fuser != null) {
            commentary.idCommentator = fuser.uid
        }
        commentary.idProfile = idPublicador
        commentary.date = date
        val reference = FirebaseDatabase.getInstance().getReference("users")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val user = snapshot.getValue(
                        User::class.java
                    )
                    if (fuser != null) {
                        if (user!!.id == fuser.uid) {
                            commentary.nameCommentator = user.nombres
                            commentary.urlImagen = user.urlImagen
                            mDatabase!!.child("Commentaries").child(commentary.idCommentary!!)
                                .setValue(commentary)
                        }
                    }
                }
                Toast.makeText(application, "Subido", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(application, "Cancelado", Toast.LENGTH_SHORT).show()
            }
        })
    }


}