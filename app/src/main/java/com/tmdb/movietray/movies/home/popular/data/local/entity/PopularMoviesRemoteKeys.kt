package com.tmdb.movietray.movies.home.popular.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Niharika.Arora
 */
@Entity(tableName = "popular_movie_remote_keys")
data class PopularMoviesRemoteKeys(
    @PrimaryKey
    val movieId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)
