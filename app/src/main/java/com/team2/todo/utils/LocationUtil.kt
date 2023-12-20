package com.team2.todo.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat

/*
* Created by Vivek Tate on 10/11/2023
* */
@SuppressLint("StaticFieldLeak")
object LocationUtil {

    private lateinit var context: Context
    private lateinit var activity: Activity

    var currentLocation: Location? by mutableStateOf<Location?>(null)
        private set

    private lateinit var locationManager: LocationManager
    private val GPS_LOCATION_PERMISSION_REQUEST = 2

    fun init(context: Context, activity: Activity, locationManager: LocationManager) {
        this.context = context
        this.activity = activity
        this.locationManager = locationManager
    }

    private fun _setLocation(newLocation: Location?) {
        currentLocation = newLocation
    }

    fun updateLocation(newLocation: Location) {
        _setLocation(newLocation)
    }

    fun valid(): Boolean {
        return currentLocation != null
    }


    fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            GPS_LOCATION_PERMISSION_REQUEST
        )
    }

    fun hasLocationPermission(): Boolean {

        return !(ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED)
    }


    fun requestLocationUpdates() {
        fetchLocationViaGPS()
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocationViaGPS() {

        if (hasLocationPermission()) {

            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val lastKnownLocationGPS =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (lastKnownLocationGPS != null) {
                updateLocation(lastKnownLocationGPS)
            }


            locationManager.requestLocationUpdates(
                LocationManager.PASSIVE_PROVIDER,
                5000,
                0F,
                GPSGeoLocationListener
            )
        } else {
            requestLocationPermission()
        }
    }
}

object GPSGeoLocationListener : LocationListener {
    override fun onLocationChanged(location: Location) {
        LocationUtil.updateLocation(location)
    }
}
