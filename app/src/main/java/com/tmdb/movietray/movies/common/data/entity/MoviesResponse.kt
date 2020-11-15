package com.tmdb.movietray.movies.common.data.entity

import com.google.gson.annotations.SerializedName

/**
 * @author Niharika.Arora
 */
data class MoviesResponse(
    @SerializedName("total_pages") val total: Int = 0,
    val page: Int = 0,
    val results: List<Movie>
) {
    data class Movie(
        val voteCount: Int,
        val posterPath: String,
        val id: Long,
        val title: String,
        val description: String,
        val popularity: Double,
        val overview: String,
    )
}