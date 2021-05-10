package com.android.tutorial.check_weather.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.tutorial.check_weather.R
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var locationProvider: FusedLocationProviderClient
    private var centerLagLng: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationProvider = LocationServices.getFusedLocationProviderClient(this)
        val isGranted = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED

        if (isGranted) {
            getCurrentLocation()
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 11111
            )
        }

        btnPickLocation.setOnClickListener {
            centerLagLng?.let { location ->
                val result = Intent()
                result.putExtra("Lat", location.latitude)
                result.putExtra("Lon", location.longitude)
                setResult(RESULT_OK, result)
                Log.d(
                        "dataChanged",
                        "choose from map > Lat > ${location.latitude} >>> Long > ${location.longitude}"
                )
                finish()
            }
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 11111 && grantResults.isNotEmpty()) {
            getCurrentLocation()
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        Toast.makeText(this, "Current Location", Toast.LENGTH_SHORT).show()
        locationProvider.lastLocation.addOnSuccessListener { location ->
            if (location == null) {
                val request = LocationRequest.create()
                request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                request.interval = 20 * 1000

                locationProvider.requestLocationUpdates(request, object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult?) {
                        super.onLocationResult(result)
                        result?.locations?.firstOrNull()?.let { location ->
                            moveCamera(LatLng(location.latitude, location.longitude))
                        }
                    }
                }, Looper.getMainLooper())
            } else {
                moveCamera(LatLng(location.latitude, location.longitude))
            }
        }
    }

    private fun moveCamera(location: LatLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f))
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
        val yangon = LatLng(16.7986, 96.1495)
        mMap.addMarker(
                MarkerOptions()
                        .position(yangon)
                        .title("Yangon")
        )
        mMap.setOnCameraIdleListener {
            centerLagLng = mMap.cameraPosition.target
        }
    }
}