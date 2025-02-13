package com.example.movie.network

import com.example.movie.model.PeopleResponse
import com.example.movie.model.Person
import com.example.movie.model.PersonDetailsResponse
import com.example.movie.model.PersonImagesResponse
import com.example.movie.model.PopularPeopleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyApiService {
    @GET("person/popular")
    suspend fun getPopularPeople(
        @Query("page") page: Int
    ): Response<PopularPeopleResponse>

    @GET("person/{person_id}/images")
    suspend fun getPersonImages(@Path("person_id") personId: Int): Response<PersonImagesResponse>

    @GET("person/{person_id}")
    suspend fun getPersonDetails(@Path("person_id") personId: Int): Response<PersonDetailsResponse>
}


