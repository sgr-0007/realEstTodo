package com.team2.todo

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.team2.todo.ui.theme.TODOTheme
import com.team2.todo.utils.GPSGeoLocationListener
import com.team2.todo.utils.NavHostControllerProvider
import com.team2.todo.utils.NavigationUtil
import com.team2.todo.utils.NotificationUtil
import com.team2.todo.utils.LocationUtil


class MainActivity : ComponentActivity() {

    private lateinit var locationManager: LocationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeLocationService()

        setContent {
            TODOTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Initialise Utils
                    val navController = rememberNavController()
                    NavigationUtil.init(navController)
                    NotificationUtil.init(this)

                    //Navigation Provider i,e the Navigation graph
                    NavHostControllerProvider()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.removeUpdates(GPSGeoLocationListener)
    }

    override fun onResume() {
        super.onResume()

        initializeLocationService()
    }

    private fun initializeLocationService() {

        locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        LocationUtil.init(this, this, locationManager)

        if(!LocationUtil.hasLocationPermission()) {
            LocationUtil.requestLocationPermission()

        } else {
            LocationUtil.requestLocationUpdates()

        }
    }
}



