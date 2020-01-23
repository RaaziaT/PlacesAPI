package com.raaziat.pagingexample.ui.weather

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.raaziat.pagingexample.R
import com.raaziat.pagingexample.ui.maps.MapsActivity
import com.raaziat.pagingexample.utils.toast
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity() {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var latLng: String

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            100
        )

        btn_restaurants.setOnClickListener { startActivity(Intent(this, MapsActivity::class.java)) }


    }

    private fun initializeRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerView_weather.layoutManager = linearLayoutManager
        recyclerView_weather.adapter = weatherAdapter
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
                                getWeather(latLng)
                            }
                        }

                    }

                } else {
                    toast("Unable to fetch current location", this)
                    latLng = "24.85777225" + "," + "67.046530902";
                    getWeather(latLng)
                }
            }
        }
    }

    private fun getWeather(latLng: String) {
        weatherViewModel.fetchWeather(latLng)

        weatherAdapter = WeatherAdapter()
        initializeRecyclerView()



        weatherViewModel.weatherLiveData.observe(this, Observer {
            weatherAdapter.submitList(it)
            weatherAdapter.setOnItemClickListener(object : WeatherAdapter.OnItemClickListener {
                override fun onItemClick(item: Int) {
                    toast(weatherAdapter.getItemAt(item).Date, this@WeatherActivity)
                }
            })
        })
    }
}
