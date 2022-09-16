package com.example.proyectoagriaplicacion.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoagriaplicacion.AddProductActivity
import com.example.proyectoagriaplicacion.R
import com.example.proyectoagriaplicacion.adapter.AdapterProduct
import com.example.proyectoagriaplicacion.adapter.ProductAdapter
import com.example.proyectoagriaplicacion.model.ListProduct
import com.google.android.gms.analytics.ecommerce.Product
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MarketFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var listaProductos: ArrayList<Product>? = null
    private var filter: ArrayList<Product>? = null
    private var context = null
    private val productAdapter: ProductAdapter? = null
    private var svSearch: SearchView? = null
    private var btnFilter: ImageButton? = null
    var frutas = true
    var hortalizas:Boolean = true
    var legumbres:Boolean = true
    var verduras:Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_market, container, false)
        val recyclerview = view.findViewById<RecyclerView>(R.id.rvProducts)
        recyclerview.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL, false)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ListProduct>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..4) {

            data.add(ListProduct("Red", "Papa", "Papa Rica", "Activo"))
            data.add(ListProduct("Red", "Papa", "Papa Rica", "Activo"))
            data.add(ListProduct("Red", "Papa", "Papa Rica", "Activo"))
            data.add(ListProduct("Red", "Papa", "Papa Rica", "Activo"))
            data.add(ListProduct("Red", "Lechuga", "Cebolla fresca", "Activo"))

        }
        // This will pass the ArrayList to our Adapter
        val adapter = AdapterProduct(data)
        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
        return view;








    }




    companion object {

    }

}