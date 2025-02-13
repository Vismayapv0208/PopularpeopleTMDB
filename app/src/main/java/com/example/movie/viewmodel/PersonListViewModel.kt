package com.example.movie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
class PersonListViewModel @Inject constructor(private val apiService: MyApiService) : ViewModel() {

    private val _filteredPeople = MutableStateFlow<PagingData<Person>>(PagingData.empty())
    val filteredPeople: StateFlow<PagingData<Person>> = _filteredPeople

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    val pagingData = Pager(
        PagingConfig(pageSize = 20, enablePlaceholders = false)
    ) {
        MyPagingSource(apiService) { message ->
            _toastMessage.postValue(message)  // Pass error message to the LiveData
        }
    }.flow.cachedIn(viewModelScope)

    fun showAllData() {
        viewModelScope.launch {
            pagingData.collectLatest { pagingData ->
                _filteredPeople.value = pagingData
            }
        }
    }

    fun setSearchQuery(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                showAllData()
            } else {
                pagingData
                    .map { pagingData ->
                        pagingData.filter { person ->
                            person.name.contains(query, ignoreCase = true) ||
                                    person.knownForDepartment.contains(query, ignoreCase = true)
                        }
                    }
                    .collectLatest { filteredPagingData ->
                        _filteredPeople.value = filteredPagingData

                    }
            }
        }
    }
}
