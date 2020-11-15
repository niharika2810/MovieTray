package com.tmdb.movietray.movies.home.toprated.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Niharika.Arora
 */
@Entity(tableName = "top_rated_movie_remote_keys")
data class TopRatedMoviesRemoteKeys(
    @PrimaryKey
    val movieId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)