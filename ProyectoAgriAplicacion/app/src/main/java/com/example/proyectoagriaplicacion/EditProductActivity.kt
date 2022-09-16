package com.example.proyectoagriaplicacion

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.DateFormat
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.proyectoagriaplicacion.model.Product
import com.example.proyectoagriaplicacion.model.User
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.util.*

class EditProductActivity : AppCompatActivity() {
    private val PICK_IMAGE = 100
    private var priceProduct: TextInputLayout? = null
    private var quantityProduct: TextInputLayout? = null
    private var descriptionProduct: TextInputLayout? = null
    private var nameProduct: TextInputLayout? = null
    var mDatabase: DatabaseReference? = null
    var firebaseUser: FirebaseUser? = null
    var storageReference: StorageReference? = null
    var idProduct: String? = ""
    var mUri: String? = null
    var imageButton: ImageButton? = null
    var imageUri: Uri? = null
    var uploadTask: UploadTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_product)

        idProduct = intent.extras!!.getString("idProduct")
        Toast.makeText(applicationContext, idProduct, Toast.LENGTH_SHORT).show()
        mDatabase = FirebaseDatabase.getInstance().reference
        storageReference = FirebaseStorage.getInstance().getReference("uploads")
        firebaseUser = FirebaseAuth.getInstance().currentUser
        nameProduct = findViewById<TextInputLayout>(R.id.nameProduct)
        descriptionProduct = findViewById<TextInputLayout>(R.id.descriptionProduct)
        quantityProduct = findViewById<TextInputLayout>(R.id.quantityProduct)
        priceProduct = findViewById<TextInputLayout>(R.id.priceProduct)
        var imageButton = findViewById<ImageButton>(R.id.ibAddProduct)
        val btnEditProduct = findViewById<Button>(R.id.btnEditProduct)
        imageButton.setOnClickListener(View.OnClickListener { openGallery() })
        btnEditProduct.setOnClickListener { uploadFirebase() }
        loadProduct()



    }

    private fun loadProduct() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Products")
        mDatabase!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    var product: Product? = Product()
                    product = dataSnapshot.getValue(Product::class.java)
                    if (product!!.idProducto == idProduct) {
                        nameProduct!!.editText!!.setText(product.nombre)
                        descriptionProduct!!.editText!!.setText(product.descripcion)
                        quantityProduct!!.editText!!.setText(product.cantidad)
                        priceProduct!!.editText!!.setText(product.precio)
                        Toast.makeText(application, "imagen" + product.urlImagen, Toast.LENGTH_SHORT).show()
                        var imageButton = findViewById<ImageButton>(R.id.ibAddProduct)
                        Glide.with(applicationContext).load(product.urlImagen).into(imageButton)

                        mUri = product.urlImagen
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error al cargar", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun uploadFirebase() {
        if (imageUri == null) {
            val product = Product()
            product.idProducto = idProduct
            product.urlImagen = mUri
            product.nombre = nameProduct!!.editText!!.text.toString()
            product.descripcion = descriptionProduct!!.editText!!.text.toString()
            product.cantidad = quantityProduct!!.editText!!.text.toString()
            product.precio = priceProduct!!.editText!!.text.toString()
            product.idUsuario = firebaseUser!!.uid
            val reference = FirebaseDatabase.getInstance().getReference("users")
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val user = snapshot.getValue(
                            User::class.java
                        )
                        if (user!!.id == firebaseUser!!.uid) {
                            product.idUsuario = user.id
                            mDatabase!!.child(product.idProducto!!).setValue(product)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(application, "Cancelado", Toast.LENGTH_SHORT).show()
                }
            })
            Toast.makeText(application, "Editado", Toast.LENGTH_SHORT).show()
            finish()
        } else{


            val nameimage = System.currentTimeMillis().toString() + ".jpg"
            uploadTask = storageReference!!.child(nameimage).putFile(imageUri!!)
            Toast.makeText(application, "Cargando", Toast.LENGTH_SHORT).show()
            val urlTask: Task<Uri> = uploadTask!!.continueWithTask(
                Continuation { task ->
                    if (!task.isSuccessful) {
                        throw task.exception!!
                    }

                    // Continue with the task to get the download URL
                    storageReference!!.child(nameimage).downloadUrl
                }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    mUri = downloadUri.toString()
                    Toast.makeText(application, "Carga completa de la imagen", Toast.LENGTH_SHORT)
                        .show()
                    val date = DateFormat.format("dd-MM-yyyy", Date()).toString()
                    val product = Product()
                    product.idProducto = idProduct
                    product.urlImagen = mUri
                    product.nombre = nameProduct!!.editText!!.text.toString()
                    product.descripcion = descriptionProduct!!.editText!!.text.toString()
                    product.cantidad = quantityProduct!!.editText!!.text.toString()
                    product.precio = priceProduct!!.editText!!.text.toString()
                    product.idUsuario = firebaseUser!!.uid
                    val reference = FirebaseDatabase.getInstance().getReference("users")
                    reference.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            for (snapshot in dataSnapshot.children) {
                                val user = snapshot.getValue(
                                    User::class.java
                                )
                                if (user!!.id == firebaseUser!!.uid) {
                                    product.idUsuario = user.id
                                    mDatabase!!.child(product.idProducto!!).setValue(product)
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
                    Toast.makeText(application, "Carga completa?????", Toast.LENGTH_SHORT).show()
                }
            }
        }


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


}