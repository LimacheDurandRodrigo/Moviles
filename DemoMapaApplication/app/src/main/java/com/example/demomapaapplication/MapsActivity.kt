package com.example.demomapaapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.demomapaapplication.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.*
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var circle:Circle?= null

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

        // Add a marker in Sydney and move the camera
        val UPT = LatLng(-18.00565, -70.22568)
        mMap.addMarker(MarkerOptions().position(UPT).title("Marker Upt"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(UPT))
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        val cameraPosition:CameraPosition = CameraPosition.builder()
            .target(UPT)
            .zoom(15f)
            .bearing(30f)
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        setMapClick(mMap)
        setMapLongClick(mMap)
    }

    private fun setMapClick(googleMap: GoogleMap){
        googleMap.setOnMapClickListener { latlong ->
            googleMap.addMarker(MarkerOptions()
                .position(latlong)
                .icon(BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_YELLOW
                ))
            )
            Toast.makeText(this,"Lat: "+latlong.latitude+","+
                    "long: "+latlong.longitude,Toast.LENGTH_SHORT).show()
            circle?.remove()
            circle = googleMap.addCircle(
                CircleOptions()
                    .center(latlong)
                    .radius(60.0)
                    .fillColor(ContextCompat.getColor(this,R.color.teal_200))
                    .strokeColor((ContextCompat.getColor(this,R.color.purple_700)))
            )
        }
    }

    private fun setMapLongClick(googleMap:GoogleMap){
        googleMap.setOnMapLongClickListener { latLong->
            val snippet:String = String.format(
                Locale.getDefault(),"Lat: %1$.5f, Long: %2$.5f",latLong.latitude,latLong.longitude)
            googleMap.addMarker(
                MarkerOptions()
                .position(latLong)
                .title("this")
                .snippet(snippet))
        }
    }
}