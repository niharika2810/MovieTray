package com.tmdb.movietray.movies.home.toprated.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tmdb.movietray.movies.common.data.entity.IMovieType

/**
 * @author Niharika.Arora
 */
@Entity(tableName = "tbl_movie_data_top_rated")
data class TopRatedMovie(
    val movieId: Long,
    val poster: String,
    @PrimaryKey
    val title: String,
    val description: String,
    val popularity: String
) : IMovieType