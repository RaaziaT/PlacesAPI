package com.raaziat.pagingexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.raaziat.pagingexample.utils.Constants

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), Constants.GOOGLE_API_KEY);
        }

        val placesClient = Places.createClient(this);

        val autocompleteSupportFragment = supportFragmentManager.findFragmentById(
            R.id.autocomplete_fragment
        ) as AutocompleteSupportFragment

        autocompleteSupportFragment.setPlaceFields(
            listOf(Place.Field.ID, Place.Field.NAME)
        )

        autocompleteSupportFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                val lat = place.latLng?.latitude
                val longi = place.latLng?.longitude
                Toast.makeText(
                    this@MainActivity,
                    "LatLong = " + lat + "," + longi,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(status: Status) {
                Toast.makeText(
                    this@MainActivity,
                    "Status = " + status,
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}
