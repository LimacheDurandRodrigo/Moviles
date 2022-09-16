package com.example.proyectoagriaplicacion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectoagriaplicacion.adapter.CommentaryAdapter
import com.example.proyectoagriaplicacion.model.Commentary
import com.example.proyectoagriaplicacion.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import java.util.ArrayList

class ProfileActivity : AppCompatActivity() {

    var tvNombres: TextView? = null
    var tvApellidos: TextView? = null
    var tvEmail: TextView? = null
    var tvDepartamento: TextView? = null
    var tvTelefono: TextView? = null
    var btnAddCommentary: Button? = null
    var profileImage: ImageView? = null
    var reference: DatabaseReference? = null
    var fuser: FirebaseUser? = null
    var storageReference: StorageReference? = null
    var idPublicador: String? = null
    var tvStars: RatingBar? = null
    var averageStars = 0f
    var numCommentaries = 0f
     var rvCommentary: RecyclerView? = null


    private lateinit var listCommentaries : ArrayList<Commentary>
    private var commentaryAdapter: CommentaryAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        var rvCommentary = findViewById<RecyclerView>(R.id.rvCommentary)

        rvCommentary.setLayoutManager(LinearLayoutManager(applicationContext))

        rvCommentary.setHasFixedSize(true)
        listCommentaries = arrayListOf<Commentary>()
        idPublicador = intent.extras!!.getString("idPublicador")
        var tvNombres = findViewById<TextView>(R.id.tvNombres)
        var tvApellidos = findViewById<TextView>(R.id.tvApellidos)
        var tvEmail = findViewById<TextView>(R.id.tvEmail)
        var tvDepartamento = findViewById<TextView>(R.id.tvDepartamento)
        var tvTelefono = findViewById<TextView>(R.id.tvTelefono)
        var profileImage = findViewById<ImageView>(R.id.profile_image)
        tvStars = findViewById(R.id.tvStars)
        btnAddCommentary = findViewById(R.id.btnAddCommentary)
        var tvStars = findViewById<RatingBar>(R.id.tvStars)
        tvStars.setEnabled(false)
        fuser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("users").child(idPublicador!!)
        loadCommentaries()
        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(
                    User::class.java
                )
                //Toast.makeText(getContext(),user.getNombres(),Toast.LENGTH_SHORT).show();
                tvNombres.setText(user!!.nombres)
                tvApellidos.setText(user.apellidos)
                tvEmail.setText(user.email)
                tvDepartamento.setText(user.departamento)
                tvTelefono.setText(user.telefono)
                Glide.with(applicationContext).load(user.urlImagen).into(profileImage)
            }

            override fun onCancelled(error: DatabaseError) {}
        })


        var btnAddCommentary = findViewById<Button>(R.id.btnAddCommentary)
        btnAddCommentary.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ProfileActivity, CommentaryActivity::class.java)

            startActivity(intent)
        })
    }

    private fun loadCommentaries() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val reference = FirebaseDatabase.getInstance().getReference("Commentaries")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listCommentaries.clear()
                for (snapshot in dataSnapshot.children) {
                    val commentaries = snapshot.getValue(Commentary::class.java)
                    if (commentaries!!.idProfile == idPublicador) {
                        listCommentaries.add(commentaries)
                        averageStars += commentaries.stars!!.toDouble().toFloat()
                        numCommentaries++
                    }
                }
                averageStars = if (numCommentaries == 0f) {
                    0f
                } else {
                    averageStars / numCommentaries
                }
                tvStars!!.rating = averageStars
                commentaryAdapter = CommentaryAdapter(applicationContext, listCommentaries)

                rvCommentary!!.adapter = commentaryAdapter
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })


    }
}