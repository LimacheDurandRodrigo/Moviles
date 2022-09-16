package com.example.proyectoagriaplicacion

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.bumptech.glide.Glide
import com.example.proyectoagriaplicacion.databinding.ActivityDetailBinding
import com.example.proyectoagriaplicacion.model.Product
import com.example.proyectoagriaplicacion.model.User
import com.getbase.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import com.example.proyectoagriaplicacion.model.Transaction
import com.example.proyectoagriaplicacion.PaymentActivity


class DetailActivity : AppCompatActivity() {

    var tvNombre: TextView? = null
    var tvDescripcion: TextView? = null
    var tvCantidad: TextView? = null
    var tvPrecio: TextView? = null
    var tvPublicador: TextView? = null
    var fabProfile: FloatingActionButton? = null
    var fabWhatsApp: FloatingActionButton? = null
    var fabPhone: FloatingActionButton? = null
    var productImage: ImageView? = null
    var id: String? = ""
    var idPublication: String? = ""
    var idPublicador: String? = ""
    var btnTransaction: Button? = null
    var mDatabase: DatabaseReference? = null
    var phoneUser = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        mDatabase = FirebaseDatabase.getInstance().reference
        id = intent.extras!!.getString("id")
        idPublication = intent.extras!!.getString("idUser")
        tvNombre = findViewById(R.id.tvNombre)
        tvDescripcion = findViewById(R.id.tvDescripcion)
        tvCantidad = findViewById(R.id.tvCantidad)
        tvPrecio = findViewById(R.id.tvPrecio)
        tvPublicador = findViewById(R.id.tvPublicador)
        productImage = findViewById(R.id.ivProduct)



        var fabProfile = findViewById<FloatingActionButton>(R.id.fabProfile)
        var fabWhatsApp = findViewById<FloatingActionButton>(R.id.fabWhatsApp)
        var fabPhone = findViewById<FloatingActionButton>(R.id.fabPhone)
        var btnTransaction = findViewById<Button>(R.id.btnTransaction)

        btnTransaction.setOnClickListener(View.OnClickListener { uploadFirebase() })
        readProduct()
        fabProfile.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@DetailActivity, ProfileActivity::class.java)
            intent.putExtra("idPublicador", idPublicador)
            startActivity(intent)
        })
        fabWhatsApp.setOnClickListener(View.OnClickListener { sendWhatsApp() })

        fabPhone.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(Intent.ACTION_DIAL).setData(
                    Uri.parse("tel:$phoneUser")

                )
            )
        })


    }

    private fun sendWhatsApp() {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        val msg = "Buen Dia, " +
                "Me interesa su producto de AgriApp."
        val uri = "whatsapp://send?phone=$phoneUser&text=$msg"
        intent.data = Uri.parse(uri)
        startActivity(intent)
    }

    private fun readProduct() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val reference = FirebaseDatabase.getInstance().getReference("Products")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val product = snapshot.getValue(Product::class.java)
                    if (product!!.idProducto == id) {
                        tvNombre!!.text = product.nombre
                        tvDescripcion!!.text = product.descripcion
                        tvCantidad!!.text = product.cantidad
                        tvPrecio!!.text = "S/ " + product.precio
                        val user = User()
                        tvPublicador!!.text = user.nombres
                        Glide.with(application).load(product.urlImagen).into(productImage!!)
                        idPublicador = product.idUsuario
                        val reference = FirebaseDatabase.getInstance().getReference("users")
                        reference.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                for (snapshot in dataSnapshot.children) {
                                    val user = snapshot.getValue(
                                        User::class.java
                                    )
                                    user!!.id = firebaseUser!!.uid
                                    if (user!!.id == idPublicador) {
                                        tvPublicador!!.text = user.nombres
                                        phoneUser = user.telefono.toString()

                                    }
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {}
                        })
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun uploadFirebase() {



    }


}