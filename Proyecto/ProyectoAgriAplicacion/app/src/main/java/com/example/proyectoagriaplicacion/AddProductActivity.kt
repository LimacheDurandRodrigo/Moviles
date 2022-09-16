package com.example.proyectoagriaplicacion

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*

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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        imageButton = findViewById(R.id.ibAddProduct)
        spinner = findViewById<View>(R.id.spinner) as Spinner
        imageButton!!.setOnClickListener { openGallery() }
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.productsSpinner, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.setAdapter(adapter)

        spinner!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }


    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }
}


