package com.example.searchmap

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationRequest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.jar.Manifest

class MainActivity : AppCompatActivity(),OnMapReadyCallback, LocationListener,GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener{

    private var mMap: GoogleMap?=null
    internal lateinit var mLastLocation:Location
    internal var CurrLocationMarker:Marker? = null
    internal var mGoogleApiClient: GoogleApiClient? = null
    internal lateinit var mLocationRequest: LocationRequest


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCES_FINE_LOCATION
            )==PackageManager.PERMISSION_GRANTED
            ){
                buildGoogleApiClient()
                mMap!!.isMyLocationEnabled = true
            }
        }
    }
    protected fun buildGoogleApiClient(){
        mGoogleApiClient =GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API).build()
        mGoogleApiClient!!.connect()

    }

    override fun onLocationChanged(location: Location) {
        mLastLocation = location

        var mCurrLocationMarker = null
        if (mCurrLocationMarker != null){
            mCurrLocationMarker!!.remove()
        }
        val latLng = LatLng(location.latitude,location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.position(latLng)
        markerOptions.title("current position")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        mCurrLocationMarker = mMap!!.addMarker(markerOptions)

        mMap !!.moveCamera(CameraUpdate Factory.newLatLng(latLng))
        mMap !!.moveCamera(CameraUpdateFactory.zoomTo(11f))
        if(mGoogleApiClient!=null){
            LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onConnected(p0: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }
}