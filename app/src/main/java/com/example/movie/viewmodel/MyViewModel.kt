package com.example.movie.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.movie.model.Person
import com.example.movie.network.MyApiService
import com.example.movie.paging.MyPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MyViewModel @Inject constructor(private val apiService: MyApiService) : ViewModel() {

    private val _filteredPeople = MutableStateFlow<PagingData<Person>>(PagingData.empty())
    val filteredPeople: StateFlow<PagingData<Person>> = _filteredPeople

    // The entire list of people (Paging Data)
    val pagingData = Pager(
        PagingConfig(pageSize = 20, enablePlaceholders = false)
    ) {
        MyPagingSource(apiService)  // Fetch the data using the API service
    }.flow.cachedIn(viewModelScope)

    // Function to show the entire list without any filtering
    fun showAllData() {
        viewModelScope.launch {
            pagingData.collectLatest { pagingData ->
                // Log the data received
                Log.e("API_RESPONSE", "Fetched data: ${pagingData}")  // Log the entire PagingData
                _filteredPeople.value = pagingData // Show all data when no search is applied
            }
        }
    }

    // Function to filter the list based on search query
    fun setSearchQuery(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                // If no search query, show all data
                showAllData()
            } else {
                // Filter data based on query
                pagingData
                    .map { pagingData ->
                        pagingData.filter { person ->
                            person.name.contains(query, ignoreCase = true) ||
                                    person.knownForDepartment.contains(query, ignoreCase = true)
                        }
                    }
                    .collectLatest { filteredPagingData ->
                        // Log the filtered data
                        Log.e("API_RESPONSE", "Filtered data: ${filteredPagingData}")  // Log the filtered PagingData
                        _filteredPeople.value = filteredPagingData
                    }
            }
        }
    }
}
