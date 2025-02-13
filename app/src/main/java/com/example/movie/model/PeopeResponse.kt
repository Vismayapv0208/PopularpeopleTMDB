package com.example.movie.model

import com.google.gson.annotations.SerializedName

data class PeopleResponse(
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
    val profilePath: String?, // Can be null
    @SerializedName("known_for")
    val knownFor: List<KnownFor>
)

data class KnownFor(
    @SerializedName("backdrop_path")
    val backdropPath: String?, // Can be null
    val id: Int,
    val title: String?, // Can be null (for movies)
    @SerializedName("original_title")
    val originalTitle: String?, // Can be null (for movies)
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?, // Can be null
    @SerializedName("media_type")
    val mediaType: String,
    val adult: Boolean,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val popularity: Double,
    @SerializedName("release_date")
    val releaseDate: String?, // Can be null (for movies)
    val video: Boolean?, // Can be null (for movies)
    @SerializedName("vote_average")
    val voteAverage: Double?, // Can be null (for movies)
    @SerializedName("vote_count")
    val voteCount: Int?, // Can be null (for movies)
    val name: String?, // Can be null (for tv)
    @SerializedName("original_name")
    val originalName: String?, // Can be null (for tv)
    @SerializedName("first_air_date")
    val firstAirDate: String?, // Can be null (for tv)
    @SerializedName("origin_country")
    val originCountry: List<String>? // Can be null (for tv)
)
data class PopularPeopleResponse(
    val results: List<Person>
)
