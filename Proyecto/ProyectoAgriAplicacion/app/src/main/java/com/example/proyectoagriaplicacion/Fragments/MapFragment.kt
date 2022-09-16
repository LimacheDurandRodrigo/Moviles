package com.example.proyectoagriaplicacion.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoagriaplicacion.R
import com.google.android.gms.analytics.ecommerce.Product
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.TileOverlay
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.firebase.database.*


class   MapFragment : AppCompatActivity(), OnMapReadyCallback {


    private var mMap: GoogleMap? = null

    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_map, container, false)


        //Internet
        //Cargar el mapa

        return root
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap



        //Habilitar zoom
        mMap!!.uiSettings.isZoomControlsEnabled = true

        //Caargar mapa de calor

        //Caargar mapa de calor
       loadHeatMap()
    }

    private fun loadHeatMap() {

        //Centrar la camara
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-16.39889, -71.535), 7f))
        //Agregar puntos
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Products")
        val list = ArrayList<LatLng>()


        //TODO: contruir el mapa de calor


    }

}