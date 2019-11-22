package com.raaziat.pagingexample.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.raaziat.pagingexample.R
import com.raaziat.pagingexample.model.places.NearbySearch
import com.raaziat.pagingexample.model.others.Spot
import com.raaziat.pagingexample.network.RetrofitClient
import com.raaziat.pagingexample.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback
//    , GoogleMap.OnMarkerClickListener
{

    private lateinit var mGoogleMap: GoogleMap
    private lateinit var mMapController: MapController
    private lateinit var latLng: String

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    private fun toast(status: String) {
        Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap

//        mGoogleMap.setOnMarkerClickListener(this);

        mMapController = MapController(this, mGoogleMap)


        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            100
        )
    }

    private fun getPlaces(latLng: String) {
        val placesCall = RetrofitClient.googleMethods().getNearbySearch(
            latLng,
            Constants.RADIUS_1000,
            Constants.TYPE,
            Constants.GOOGLE_API_KEY
        )

        placesCall.enqueue(object : Callback<NearbySearch> {
            override fun onFailure(call: Call<NearbySearch>, t: Throwable) {

            }

            override fun onResponse(call: Call<NearbySearch>, response: Response<NearbySearch>) {
                val nearbySearch = response.body()!!

                if (nearbySearch.status == "OK") {
                    val spotList = ArrayList<Spot>()

                    for (resultItem in nearbySearch.results) {
                        val spot = Spot(
                            resultItem.name,
                            resultItem.geometry.location.lat,
                            resultItem.geometry.location.lng
                        )
                        spotList.add(spot)
                    }

                    mMapController.setMarkersAndZoom(spotList)
                } else {
                    toast(nearbySearch.status)
                }
            }

        })
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
                                placeMarker(location?.latitude as Double,location.longitude)
                                getPlaces(latLng)
                            }
                        }

                    }

                } else {
                    toast("Unable to fetch current location")
                    latLng = "24.85777225" + "," + "67.046530902";
                    placeMarker(24.85777225,67.046530902)
                    getPlaces(latLng)
                }
            }
        }
    }

    private fun placeMarker(lat:Double, longitude:Double){
        val latLng = LatLng(lat, longitude)
        mGoogleMap.addMarker(MarkerOptions().position(latLng).title("Location"))
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
    }

//    override fun onMarkerClick(marker: Marker?): Boolean {
//        val latilongi:LatLng? = marker?.position
//        return true
//    }


}

