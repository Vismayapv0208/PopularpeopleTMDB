package com.example.movie.viewmodel

import android.util.Log
import android.widget.Toast
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

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    fun getPersonImages(personId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getPersonImages(personId)
                if (response.isSuccessful) {
                    _personImages.value = response.body()?.profiles ?: emptyList()
                } else {
                    _toastMessage.value = " Error : ${response.message()}"
                }
            } catch (e: Exception) {
                _toastMessage.value = "Error : ${e.message}"

            }
        }
    }

    fun getPersonDetails(personId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getPersonDetails(personId)
                if (response.isSuccessful) {
                    _personDetails.value = response.body()
                } else {
                    Log.e("tototto",_toastMessage.value.toString())
                    _toastMessage.value = "Error: ${response.code()} - ${response.body()}"
                }
            } catch (e: Exception) {
                Log.e("tototto",_toastMessage.value.toString())

                _toastMessage.value = "Error : ${e.message}"
            }
        }
    }
}
