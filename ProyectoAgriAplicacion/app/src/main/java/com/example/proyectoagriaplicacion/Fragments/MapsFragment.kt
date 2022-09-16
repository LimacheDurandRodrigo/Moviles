package com.example.proyectoagriaplicacion.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.proyectoagriaplicacion.R
import com.example.proyectoagriaplicacion.model.Product
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.TileOverlay
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.maps.android.heatmaps.HeatmapTileProvider
import java.util.ArrayList


class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map:GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_maps, container, false)

        createFragment()


        return root;

    }

    private fun createFragment() {

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap


        map.getUiSettings().setZoomControlsEnabled(true)
        loadHeatMap()

    }
    private fun loadHeatMap() {
        //Centrar la camara
        map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-16.39889, -71.535), 7f))
        //Agregar puntos
        val reference = FirebaseDatabase.getInstance().getReference("Products")
        val list = ArrayList<LatLng>()
        reference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val product = snapshot.getValue(Product::class.java)
                    if (product!!.lat != null) {
                        val position = LatLng(product.lat!!.toDouble(), product.lng!!.toDouble())
                        list.add(position)
                        map!!.addMarker(MarkerOptions().position(position).title(product.nombre))


                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {}
        })


    }



}




