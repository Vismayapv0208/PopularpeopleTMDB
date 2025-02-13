package com.example.movie.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.model.PersonDetailsResponse
import com.example.movie.model.ProfileImage
import com.example.movie.network.MyApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val apiService: MyApiService
) : ViewModel() {

    private val _personImages = MutableLiveData<List<ProfileImage>>()
    val personImages: LiveData<List<ProfileImage>> get() = _personImages

    private val _personDetails = MutableLiveData<PersonDetailsResponse>()
    val personDetails: LiveData<PersonDetailsResponse> get() = _personDetails

    fun getPersonImages(personId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getPersonImages(personId)
                if (response.isSuccessful) {
                    Log.d("API_SUCCESS1", "Person images fetched successfully: ${response.body()}")
                    _personImages.value = response.body()?.profiles ?: emptyList()
                } else {
                    Log.e("API_ERROR1", "Failed to fetch person images: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("" +
                        "" +
                        "", "Error fetching person images: ${e.message}")
            }
        }
    }

    fun getPersonDetails(personId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getPersonDetails(personId)
                if (response.isSuccessful) {
                    Log.d("API_SUCCESS2", "Person details fetched successfully: ${response.body()}")
                    _personDetails.value = response.body()
                } else {
                    Log.e("API_ERROR2", "Failed to fetch person details: ${response.code()} - ${response.body()}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR2", "Error fetching person details: ${e.message}")
            }
        }
    }
}
