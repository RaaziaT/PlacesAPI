package com.raaziat.pagingexample.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.GoogleMap
import com.raaziat.pagingexample.R
import com.raaziat.pagingexample.utils.toast
import kotlinx.android.synthetic.main.activity_restaurant.*


class MapsActivity : AppCompatActivity() {

    private lateinit var latLng: String
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: MapsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        viewModel = ViewModelProviders.of(this).get(MapsViewModel::class.java)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            100
        )
    }


    private fun getPlaces(latLng: String) {
        viewModel.fetchPlaces(latLng)
        viewModel.placesLiveData.observe(this, Observer {
            recyclerView_restaurant.layoutManager = LinearLayoutManager(this)
            val adapter = RestaurantAdapter(it, this)
            recyclerView_restaurant.adapter = adapter
        }
        )

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                            run {
                                latLng =
                                    location?.latitude.toString() + "," + location?.longitude.toString()
                                getPlaces(latLng)
                            }
                        }

                    }

                } else {
                    toast("Unable to fetch current location",this)
                }
            }
        }
    }


}

