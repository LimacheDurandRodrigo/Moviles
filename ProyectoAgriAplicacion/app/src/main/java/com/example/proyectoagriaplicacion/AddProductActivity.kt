package com.example.proyectoagriaplicacion

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.DateFormat
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.example.proyectoagriaplicacion.model.Product
import com.example.proyectoagriaplicacion.model.User
import com.google.android.gms.common.internal.Constants
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.text.SimpleDateFormat
import java.util.*

class AddProductActivity : AppCompatActivity() {

    private val PICK_IMAGE = 100
    var imageButton: ImageButton? = null
    var spinner: Spinner? = null
    var imageUri: Uri? = null
    var mUri: String? = null
    var nameProduct: TextInputLayout? = null
    var descriptionProduct:TextInputLayout? = null
    var quantityProduct:TextInputLayout? = null
    var priceProduct:TextInputLayout? = null
    var recommendedPrice: TextView? = null
    var mDatabase: DatabaseReference? = null
    var firebaseUser: FirebaseUser? = null
    var storageReference: StorageReference? = null
    var uploadTask: UploadTask? = null
    var price = 0f
    var numPrice:Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        mDatabase = FirebaseDatabase.getInstance().reference
        storageReference = FirebaseStorage.getInstance().getReference("uploads")
        firebaseUser = FirebaseAuth.getInstance().currentUser

        descriptionProduct = findViewById(R.id.descriptionProduct)
        quantityProduct = findViewById(R.id.quantityProduct)
        priceProduct = findViewById(R.id.priceProduct)
        imageButton = findViewById(R.id.ibAddProduct)
        spinner = findViewById<View>(R.id.spinner) as Spinner

        imageButton!!.setOnClickListener { openGallery() }

        recommendedPrice = findViewById(R.id.recommendedPrice)
        val btnAddProduct = findViewById<Button>(R.id.btnAddProduct)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.productsSpinner, android.R.layout.simple_spinner_item
        )

        btnAddProduct.setOnClickListener(View.OnClickListener { uploadFirebase() })

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.setAdapter(adapter)

        spinner!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                checkPrice()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }
    fun checkPrice() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Products")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                price = 0f
                numPrice = 0f
                for (dataSnapshot in snapshot.children) {
                    val product = dataSnapshot.getValue(Product::class.java)
                    if (product!!.nombre.equals(spinner!!.selectedItem.toString())) {

                        price += product.precio!!.toFloat() ?:
                        numPrice++
                    }
                }
                if (numPrice == 0f) {
                    recommendedPrice!!.text = "No existen precios disponibles sobre este producto."
                } else {
                    price = price / numPrice
                    recommendedPrice!!.text = "El Precio recomendado es: S/ $price"
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data!!.data
            imageButton!!.setImageURI(imageUri)
        }
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }

    private fun uploadFirebase() {
        if (imageUri == null && spinner!!.selectedItemPosition != 0) {
            Toast.makeText(application, "Faltan datos", Toast.LENGTH_SHORT).show() 
        } else {

            Toast.makeText(getApplication(),imageUri.toString(),Toast.LENGTH_SHORT).show();

            val nameimage = System.currentTimeMillis().toString() + ".jpg"
            uploadTask = storageReference!!.child(nameimage).putFile(imageUri!!)
            Toast.makeText(application, "Cargando", Toast.LENGTH_SHORT).show()
            val urlTask = uploadTask!!.continueWithTask { task ->
                if (!task.isSuccessful) {
                    throw task!!.exception!!
                }
                // Continue with the task to get the download URL
                storageReference!!.child(nameimage).downloadUrl


            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    mUri = downloadUri.toString()
                    Toast.makeText(application, "Carga completa de la imagen", Toast.LENGTH_SHORT)
                        .show()

                    val fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        .format(Date())
                    val product = Product()
                    product.idProducto = UUID.randomUUID().toString()
                    product.urlImagen = mUri
                    product.nombre = spinner!!.selectedItem.toString()
                    when ((spinner!!.selectedItemPosition - 1) / 7) {
                        0 -> product.tipo =
                            resources.getStringArray(R.array.productsType)[0]
                        1 -> product.tipo = resources.getStringArray(R.array.productsType)[1]
                        2 -> product.tipo = resources.getStringArray(R.array.productsType)[2]
                        3 -> product.tipo = resources.getStringArray(R.array.productsType)[3]
                    }
                    product.descripcion = descriptionProduct!!.editText!!.text.toString()
                    product.cantidad = quantityProduct!!.editText!!.text.toString() + " Kg"
                    product.precio = priceProduct!!.editText!!.text.toString()
                    product.estado = "Activo"
                    product.fecha = fecha
                    val reference = FirebaseDatabase.getInstance().getReference("users")

                    reference.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            for (snapshot in dataSnapshot.children) {

                                val user = snapshot.getValue(User::class.java)
                                user!!.id = firebaseUser!!.uid
                                var i = 0

                                if (user != null) {
                                    if (user.id == firebaseUser!!.uid) {
                                        product.idUsuario = user.id
                                        product.departamento = user.departamento
                                        for (departamento in resources.getStringArray(R.array.departamentos)) {
                                            if (departamento == user.departamento) {
                                                product.lat = resources.getStringArray(R.array.lat)[i]
                                                product.lng = resources.getStringArray(R.array.lng)[i]
                                            }
                                            i++
                                        }
                                        mDatabase!!.child("Products").child(product.idProducto!!)
                                            .setValue(product)
                                    }
                                }

                            }
                        }


                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(application, "Cancelado", Toast.LENGTH_SHORT).show()
                        }
                    })
                    Toast.makeText(application, "Subido", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(application, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}


