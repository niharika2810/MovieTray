package com.tmdb.movietray.movies.home.popular.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tmdb.movietray.movies.common.data.entity.IMovieType

/**
 * @author Niharika.Arora
 */
@Entity(tableName = "tbl_movie_data_popular")
data class PopularMovie(
    val movieId: Long,
    val poster: String,
    @PrimaryKey
    val title: String,
    val description: String,
) : IMovieType