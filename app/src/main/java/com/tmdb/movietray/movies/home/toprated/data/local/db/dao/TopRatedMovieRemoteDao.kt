package com.tmdb.movietray.movies.home.toprated.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tmdb.movietray.movies.home.toprated.data.local.db.entity.TopRatedMoviesRemoteKeys

@Dao
interface TopRatedMovieRemoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<TopRatedMoviesRemoteKeys>)

    @Query("SELECT * FROM top_rated_movie_remote_keys WHERE movieId = :movieId")
    suspend fun remoteKeysByMovieId(movieId: Long): TopRatedMoviesRemoteKeys?

    @Query("DELETE FROM top_rated_movie_remote_keys")
    suspend fun clearRemoteKeys()
}