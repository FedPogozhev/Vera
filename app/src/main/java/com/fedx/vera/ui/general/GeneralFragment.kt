package com.fedx.vera.ui.general

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.fedx.vera.MainActivity
import com.fedx.vera.R
import com.fedx.vera.databinding.GeneralFragmentBinding
import com.fedx.vera.geo_data.GeoData
import com.fedx.vera.ui.products.tableware.AdapterGoods
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.general_fragment.view.*
import java.io.IOException

class GeneralFragment : Fragment() {
    val PERMISSION_ID = 45

    companion object {
        var cityAdmin: String = ""
    }

    lateinit var mFusedLocationClient: FusedLocationProviderClient

    private val viewModel: GeneralViewModel by lazy {
        ViewModelProviders.of(this).get(GeneralViewModel::class.java)
    }
    lateinit var binding: GeneralFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mFusedLocationClient =
            activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!
        getLastLocation()
        binding = GeneralFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        Log.d("location", "get city ${viewModel.city.value}")

        val toolbar = binding.tbGeneral

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

        binding.viewModel = viewModel
        binding.searchEditText.setOnKeyListener (View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                viewModel.getGeneralsSearch(binding.searchEditText.text.toString())
                val inputMethodManager = (activity as AppCompatActivity)
                    .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })
        binding.rvGeneral.adapter = AdapterGoods()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.top_menu, menu)
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                activity?.let {
                    mFusedLocationClient.lastLocation.addOnCompleteListener(it) { task ->
                        var location: Location? = task.result
                        if (location == null) {
                            requestNewLocationData()
                            Log.d("Location", "Location == рул")
                        } else {
                            Log.d("Location", "Location != рул")
                            //location.latitude
                            Log.d("Location", "lat ${location.latitude.toString()}")
                            Log.d("Location", "lon ${location.longitude}")
                            val geocoder = Geocoder(it)
                            var addresses: List<Address>? = null
                            val address: Address?
                            try {
                                addresses = geocoder.getFromLocation(
                                    location.latitude,
                                    location.longitude,
                                    1
                                )
                                //address = addresses[0]
                                address = addresses[0]
                                Log.d("Location", " city ${address.adminArea}")
                                cityAdmin = address.adminArea

                                //                            val editor = preferences.edit()
                                //                            editor.putString(APP_PREFERENCES_CITY, city)
                                //                            editor.apply()

                                Log.d("Location", " address ${address.adminArea}")

                            } catch (e: IOException) {
                                e.message.toString()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(activity, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
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

        mFusedLocationClient =
            activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!
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
        if (activity?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } == PackageManager
                .PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager
                .PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_ID
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = activity?.getSystemService(Context.LOCATION_SERVICE)
                as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}
