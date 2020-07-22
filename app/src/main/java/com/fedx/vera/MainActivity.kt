package com.fedx.vera

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import android.provider.Settings
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.*
import java.io.IOException
class MainActivity : AppCompatActivity() {

    val PERMISSION_ID = 45
    companion object{
        var cityAdmin: String = ""
    }

    lateinit var mFusedLocationClient: FusedLocationProviderClient

    val APP_PREFERENCES = "setting"
    val APP_PREFERENCES_CITY = "city"
    lateinit var preferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //getLastLocation()
        preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        if (preferences.contains(APP_PREFERENCES_CITY)){
            cityAdmin = preferences.getString(APP_PREFERENCES_CITY, "").toString()
            Log.d("Location preferences", "city = $cityAdmin")
        }

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf
                (
                R.id.navigation_general, R.id.navigation_products, R.id.navigation_service,
                R.id.navigation_basket, R.id.navigation_profile
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }
    @SuppressLint("MissingPermission")
    fun getLastLocation(){
        if (checkPermission()){
            if (isLocationEnabled()){
                mFusedLocationClient.lastLocation.addOnCompleteListener(this){task ->
                    var location: Location? = task.result
                    if (location == null){
                        requestNewLocationData()
                        Log.d("Location", "Location == рул")
                    }else{
                        Log.d("Location", "Location != рул")
                        //location.latitude
                        Log.d("Location", "lat ${location.latitude.toString()}")
                        Log.d("Location", "lon ${location.longitude}")
                        val geocoder = Geocoder(this)
                        var addresses: List<Address>? = null
                        val address: Address?
                        try {
                            addresses = geocoder.getFromLocation(location.latitude, location.longitude,1)
                            //address = addresses[0]
                            address = addresses[0]
                            Log.d("Location", " city ${address.adminArea}")
                            cityAdmin = address.adminArea

                            Log.d("Location", " address ${address.adminArea}")

                        }catch (e: IOException){
                            e.message.toString()
                        }
                    }
                }
            }else{
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }else{
            requestPermissions()
        }
    }


    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            Log.d("Location", "new lat ${mLastLocation.latitude.toString()}")
            Log.d("Location", "new lon ${mLastLocation.longitude.toString()}")
        }
    }

    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager
                .PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager
                .PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID){
            if ((grantResults.isNotEmpty()&&grantResults[0] == PackageManager.PERMISSION_GRANTED)){

            }
        }
    }
    private fun isLocationEnabled (): Boolean{
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE)
        as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}
