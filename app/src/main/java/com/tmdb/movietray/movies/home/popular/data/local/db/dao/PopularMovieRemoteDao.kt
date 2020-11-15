package com.tmdb.movietray.movies.home.popular.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tmdb.movietray.movies.home.popular.data.local.entity.PopularMoviesRemoteKeys

/**
 * @author Niharika.Arora
 */
@Dao
interface PopularMovieRemoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<PopularMoviesRemoteKeys>)

    @Query("SELECT * FROM popular_movie_remote_keys WHERE movieId = :movieId")
    suspend fun remoteKeysByMovieId(movieId: Long): PopularMoviesRemoteKeys?

    @Query("DELETE FROM popular_movie_remote_keys")
    suspend fun clearRemoteKeys()
}