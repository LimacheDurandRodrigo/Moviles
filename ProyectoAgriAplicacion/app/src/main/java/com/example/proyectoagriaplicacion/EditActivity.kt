package com.example.proyectoagriaplicacion

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.proyectoagriaplicacion.model.Product
import com.example.proyectoagriaplicacion.model.User
import com.getbase.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EditActivity : AppCompatActivity() {

    var tvNombre: TextView? = null
    var tvDescripcion: TextView? = null
    var tvCantidad: TextView? = null
    var tvPrecio: TextView? = null
    var tvPublicador: TextView? = null
    var fabProfile: FloatingActionButton? = null
    var fabWhatsApp: FloatingActionButton? = null
    var fabPhone: FloatingActionButton? = null
    var productImage: ImageView? = null
    var idProduct: String? = ""
    var idPublication: String? = ""
    var idPublicador: String? = ""
    var btnEdit: Button? = null
    var btnDelete: Button? = null
    var phoneUser = "123456789"
    var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        mDatabase = FirebaseDatabase.getInstance().reference
        idProduct = intent.extras!!.getString("id")
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
        var btnEdit = findViewById<Button>(R.id.btnEdit)
        var btnDelete = findViewById<Button>(R.id.btnDelete)
        readProduct()
        btnEdit.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@EditActivity, EditProductActivity::class.java)
            intent.putExtra("idProduct", idProduct)
            startActivity(intent)
        })
        btnDelete.setOnClickListener(View.OnClickListener {

            val reference = FirebaseDatabase.getInstance().getReference("Products")
            reference.child(idProduct!!).removeValue()
            Toast.makeText(applicationContext, "Eliminado", Toast.LENGTH_SHORT).show()
            finish()
        })
    }

    private fun readProduct() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val reference = FirebaseDatabase.getInstance().getReference("Products")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val product = snapshot.getValue(Product::class.java)
                    if (product!!.idProducto == idProduct) {
                        tvNombre!!.text = product.nombre
                        tvDescripcion!!.text = product.descripcion
                        tvCantidad!!.text = product.cantidad
                        tvPrecio!!.text = "S/ " + product.precio
                        Glide.with(application).load(product.urlImagen).into(productImage!!)
                        idPublicador = product.idUsuario
                        val reference = FirebaseDatabase.getInstance().getReference("users")
                        reference.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                for (snapshot in dataSnapshot.children) {
                                    val user = snapshot.getValue(
                                        User::class.java
                                    )
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

    private fun sendWhatsApp() {
        TODO("Not yet implemented")
    }
}