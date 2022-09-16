package com.example.proyectoagriaplicacion.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoagriaplicacion.AddProductActivity
import com.example.proyectoagriaplicacion.R
import com.example.proyectoagriaplicacion.adapter.ProductAdapter
import com.example.proyectoagriaplicacion.model.Product
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore


class MarketFragment : Fragment() {

    private lateinit var recyclerview:RecyclerView
    private lateinit var listaProductos : ArrayList<Product>
    private var filter: ArrayList<Product>? = null
    private var context = null
    private var productAdapter: ProductAdapter? = null
    private var svSearch: SearchView? = null
    private var btnFilter: ImageButton? = null

    var frutas = true
    var hortalizas:Boolean = true
    var legumbres:Boolean = true
    var verduras:Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_market, container, false)

        recyclerview = root.findViewById(R.id.rvProducts)
        recyclerview.layoutManager = LinearLayoutManager(this.activity,LinearLayoutManager.VERTICAL, false)
        recyclerview.setHasFixedSize(true)

        svSearch = root.findViewById(R.id.svSearch)
        btnFilter = root.findViewById(R.id.btnFilter)


        listaProductos = arrayListOf<Product>()
        filter = java.util.ArrayList()

        val data = ArrayList<Product>()

        this.readPublications()


        val addProduct: FloatingActionButton = root.findViewById(R.id.addProduct)
        addProduct.setOnClickListener {
            val intent = Intent(this.requireContext(), AddProductActivity::class.java)
            this.startActivity(intent)
        }
        var svSearch = root.findViewById<SearchView>(R.id.svSearch)
       svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchProduct(newText)
                return true
            }
        })


        var btnFilter = root.findViewById<ImageButton>(R.id.btnFilter)
        btnFilter.setOnClickListener { v -> this.onButtonShowPopupWindowClick(v) }
        return root;

    }

    private fun readPublications() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        val reference = FirebaseDatabase.getInstance().getReference("Products")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (ProducSnapshot in snapshot.children){
                        val products = ProducSnapshot.getValue(Product::class.java)

                        if (products != null) {
                            listaProductos.add(products)
                        }


                    }
                    recyclerview.adapter = ProductAdapter(requireContext(),listaProductos)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



    }



    private fun onButtonShowPopupWindowClick(v: View?) {
        // inflate the layout of the popup window

        val inflater = this.requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_filter, null)


        // create the popup window
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true
        // lets taps outside the popup also dismiss it

        val popupWindow = PopupWindow(popupView, width, height, focusable)


        val cbFrutas = popupView.findViewById<CheckBox>(R.id.frutas)
        val cbHortalizas = popupView.findViewById<CheckBox>(R.id.hortalizas)
        val cbLegumbres = popupView.findViewById<CheckBox>(R.id.legumbres)
        val cbVerduras = popupView.findViewById<CheckBox>(R.id.verduras)

        cbFrutas.isChecked = this.frutas
        cbHortalizas.isChecked = this.hortalizas
        cbLegumbres.isChecked = this.legumbres
        cbVerduras.isChecked = this.verduras

        cbFrutas.setOnClickListener {
            frutas = !frutas
            if (frutas) {
                addProduct("Frutas")
            } else {
                removeProduct("Frutas")
            }
        }
        cbHortalizas.setOnClickListener {
            hortalizas = !hortalizas
            if (hortalizas) {
                addProduct("Hortalizas")
            } else {
                removeProduct("Hortalizas")
            }
        }
        cbLegumbres.setOnClickListener {
            legumbres = !legumbres
            if (legumbres) {
                addProduct("Legumbres")
            } else {
                removeProduct("Legumbres")
            }
        }
        cbVerduras.setOnClickListener {
            verduras = !verduras
            if (verduras) {
                addProduct("Verduras")
            } else {
                removeProduct("Verduras")
            }
        }



        popupWindow.showAtLocation(this.view, View.TEXT_ALIGNMENT_CENTER, 0, -430)


        popupView.setOnTouchListener { v, event ->
            popupWindow.dismiss()
            true
        }
    }

    private fun removeProduct(type: String) {
        val filterProducts = ArrayList<Product>()
        for (obj in listaProductos!!) {
            if (obj!!.tipo != null) {
                if (obj.tipo == type) {
                    filter!!.remove(obj)
                }
            }
        }
        val adapterProduct = filter?.let { ProductAdapter(requireContext(), it) }
        recyclerview.adapter = adapterProduct
    }

    private fun addProduct(type: String) {
        val filterProducts = ArrayList<Product>()
        for (obj in listaProductos!!) {
            if (obj!!.tipo != null) {
                if (obj.tipo == type) {
                    filter!!.add(obj)
                }
            }
        }
        val adapterProduct = filter?.let { ProductAdapter(requireContext(), it) }
        recyclerview.adapter = adapterProduct
    }

    private fun searchProduct(newText: String) {
        val filterProducts = ArrayList<Product>()
        for (obj in listaProductos!!) {
            if (obj!!.nombre!!.toLowerCase()
                    .contains(newText.toLowerCase()) || obj.precio!!.toLowerCase()
                    .contains(newText.toLowerCase()) || obj.descripcion!!.toLowerCase()
                    .contains(newText.toLowerCase())
            ) {
                filterProducts.add(obj)
            }
        }
        val adapterProduct = ProductAdapter(requireContext(), filterProducts)
        recyclerview.adapter = adapterProduct

    }




}