package com.tmdb.movietray.movies.home.toprated.data.local.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tmdb.movietray.movies.home.toprated.data.local.db.entity.TopRatedMovie

@Dao
interface TopRatedMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movieResponse: List<TopRatedMovie>)

    @Query("select * from tbl_movie_data_top_rated")
    fun getMovies(): PagingSource<Int, TopRatedMovie>

    @Query("DELETE FROM tbl_movie_data_top_rated")
    suspend fun clearMovies()
}