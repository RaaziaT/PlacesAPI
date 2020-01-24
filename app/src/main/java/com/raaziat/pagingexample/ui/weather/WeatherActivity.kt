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
import android.location.Geocoder
import android.widget.Toast
import com.raaziat.pagingexample.model.openweather.X
import com.raaziat.pagingexample.utils.Constants
import com.raaziat.pagingexample.utils.formatList
import com.raaziat.pagingexample.utils.getCelsius
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_weather.txtView_day
import kotlinx.android.synthetic.main.item_weather.*
import java.util.*


class WeatherActivity : AppCompatActivity() {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var weatherAdapter: WeatherAdapter

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
                                getWeather(location?.latitude, location?.longitude)
                            }
                        }

                    }

                } else {
                    toast("Unable to fetch current location", this)
                }
            }
        }
    }

    private fun getCityName(lat: Double?, lng: Double?): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(lat!!, lng!!, 1)
        val cityName = addresses[0].locality
        return cityName
    }

    private fun getWeather(lat: Double?, lng: Double?) {
        weatherViewModel.fetchWeather(lat, lng)

        weatherAdapter = WeatherAdapter()
        initializeRecyclerView()



        weatherViewModel.weatherLiveData.observe(this, Observer {

            if (it != null) {
                val list:List<X> =  formatList(it.list)
                weatherAdapter.submitList(list)

                txtView_city.text = getCityName(lat, lng)
                txtView_temp.text = getCelsius(it.list[0].main.temp).toString().dropLast(2)
                txtView_day.text = Calendar.getInstance()
                    .getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US)

                Picasso.get().load(Constants.OPEN_WEATHER_ICON_BASE_URL + it.list[0].weather.get(0).icon + ".png").
                    into(imageView)

                weatherAdapter.setOnItemClickListener(object : WeatherAdapter.OnItemClickListener {
                    override fun onItemClick(item: Int) {
                        toast(weatherAdapter.getItemAt(item).dt_txt, this@WeatherActivity)
                    }
                })
            } else Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}
