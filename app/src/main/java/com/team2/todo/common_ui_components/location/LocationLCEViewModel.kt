package com.team2.todo.common_ui_components.location

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * Created by Manu KJ on 11/17/23.
 */

class LocationLCEViewModel : ViewModel() {
    private var _isLoading by mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading

    fun updateLoadingState(isLoading: Boolean) {
        _isLoading = isLoading
    }

    private var _fetchedLocation by mutableStateOf<Location?>(null);
    val fetchedLocation: Location? get() = _fetchedLocation
    fun updateFetchedLocation(location: Location) {
        _fetchedLocation = location;
    }

    fun isLocationPresent(): Boolean {
        return _fetchedLocation != null;
    }

}