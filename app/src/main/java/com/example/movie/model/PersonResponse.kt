package com.example.movie.model

import com.google.gson.annotations.SerializedName

data class PersonResponse(
    val page: Int,
    val results: List<Person>
)

data class Person(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerializedName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("known_for")
    val knownFor: List<KnownFor>
)

data class KnownFor(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val id: Int,
    val title: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("media_type")
    val mediaType: String,
    val adult: Boolean,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val popularity: Double,
    @SerializedName("release_date")
    val releaseDate: String?,
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?,
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("origin_country")
    val originCountry: List<String>?
)
data class PopularPeopleResponse(
    val results: List<Person>
)
