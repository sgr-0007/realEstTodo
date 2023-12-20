package com.team2.todo.utils

import android.location.Location
import com.team2.todo.data.entities.relations.TodoWithSubTodos
import kotlin.math.*

object GeoFenceUtil {

    fun sortLocationByDistance(
        list: List<TodoWithSubTodos>,
        centerLocation: Location
    ): List<TodoWithSubTodos> {

        val distances = list.map {
            it.todo.longitude?.let { longitude ->
                it.todo.latitude?.let { latitude ->
                    calculateDistance(latitude, longitude, centerLocation)
                }
            }
        }

        val listDistancePair = list.zip(distances)

        return ((listDistancePair.sortedBy { it.second }).map { it.first })
    }

    // calculates the distance between 2 point and returns the distance in meter
    fun calculateDistance(
        latitude: Double,
        longitude: Double,
        centerLocation: Location
    ): Double {
        val earthRadius = 6371

        val latitudeDifference = Math.toRadians(latitude - centerLocation.latitude)
        val longitudeDifference = Math.toRadians(longitude - centerLocation.longitude)

        val result = sin(latitudeDifference / 2) * sin(latitudeDifference / 2) +
                cos(Math.toRadians(centerLocation.latitude)) * cos(Math.toRadians(latitude)) *
                sin(longitudeDifference / 2) * sin(longitudeDifference / 2)

        return earthRadius * (2 * atan2(sqrt(result), sqrt(1 - result))) * 1000
    }
}