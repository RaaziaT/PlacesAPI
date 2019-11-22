package com.raaziat.pagingexample.ui.maps

import android.content.Context
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.raaziat.pagingexample.R
import com.raaziat.pagingexample.model.others.Spot

class MapController(context: Context, googleMap: GoogleMap) {

    private val mContext: Context = context
    private val mGoogleMap: GoogleMap = googleMap
    private var mSpotMarkerList = ArrayList<Marker>()
    private val mTimeSquare = LatLng(40.758895, -73.985131)

    fun setMarkersAndZoom(spotList: List<Spot>) {
        val spotBitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_spot_marker)


        for (spot in spotList) {
            val name = spot.name
            val latitude = spot.lat
            val longitude = spot.lng
            val latLng = LatLng(latitude!!, longitude!!)
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng).title(name).icon(spotBitmap)

            val marker = mGoogleMap.addMarker(markerOptions)
            mSpotMarkerList.add(marker)
        }

        mGoogleMap.animateCamera(autoZoomLevel(mSpotMarkerList))
    }

    fun clearMarker() {
        for (marker in mSpotMarkerList) {
            marker.remove()
        }
        mSpotMarkerList.clear()
    }

    fun animateCamera() {
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mTimeSquare, 15f))
    }


    private fun autoZoomLevel(markerList: List<Marker>): CameraUpdate {
        if (markerList.size == 1) {
            val latitude = markerList[0].position.latitude
            val longitude = markerList[0].position.longitude
            val latLng = LatLng(latitude, longitude)

            return CameraUpdateFactory.newLatLngZoom(latLng, 13f)
        } else {
            val builder = LatLngBounds.Builder()
            for (marker in markerList) {
                builder.include(marker.position)
            }
            val bounds = builder.build()

            val padding = 200 // offset from edges of the map in pixels

            return CameraUpdateFactory.newLatLngBounds(bounds, padding)
        }
    }

//    fun setCustomMarker() {
//        val blackMarkerIcon : BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_custom_marker)
//        val markerOptions : MarkerOptions = MarkerOptions().position(mTimeSquare).title(mContext.getString(R.string.time_square)).snippet(mContext.getString(R.string.i_am_snippet)).icon(blackMarkerIcon)
//        mGoogleMap.addMarker(markerOptions)
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(mTimeSquare))
//    }
}
