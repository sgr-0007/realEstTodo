package com.team2.todo.common_ui_components.filter.view_model


import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import android.content.SharedPreferences
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit

/*
* Created by Vivek Tate on 18/11/2023
*/

class FilterViewModel(private val context: Context): ViewModel() {

    private val SELECTED_FILTER_KEY = "selectedFilter"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("FilterPreferences", Context.MODE_PRIVATE)

    private val _selectedFilter = mutableStateOf(getSavedFilter())
    val selectedFilter: State<Filter>
        get() = _selectedFilter

    fun setSelectedFilter(filter: Filter) {
        _selectedFilter.value = filter

        sharedPreferences.edit {
            putString(SELECTED_FILTER_KEY, filter.value)
        }
    }

    fun getAllFilters(): List<Filter>{
        return listOf(
            Filter.DEFAULT_FILTER,
            Filter.DUE_DATE,
            Filter.HIGH_PRIORITY,
            Filter.LOW_PRIORITY,
            Filter.GEO_LOCATION,
            Filter.HIGH_PRICE,
            Filter.LOW_PRICE
        )
    }

    fun getFilter(value: String): Filter? {
        val map = Filter.values().associateBy(Filter::value)
        return map[value]
    }

    private fun getSavedFilter(): Filter {
        val filterValue = sharedPreferences.getString(SELECTED_FILTER_KEY, null)
        return filterValue?.let { getFilter(it) } ?: getAllFilters()[0]
    }

}

enum class Filter(val value: String) {

    DEFAULT_FILTER("Date of Creation"),
    GEO_LOCATION("Nearby Location"),
    DUE_DATE("Deadline"),
    HIGH_PRIORITY("Priority: High to Low"),
    LOW_PRIORITY("Priority: Low to High"),
    HIGH_PRICE("Price: High to Low"),
    LOW_PRICE("Price: Low to High")
}