package com.tmdb.movietray.movies.home.toprated.data.apihelper

import androidx.paging.PagingData
import com.tmdb.movietray.movies.home.toprated.data.local.db.entity.TopRatedMovie
import kotlinx.coroutines.flow.Flow

/**
 * @author Niharika.Arora
 */
interface TopRatedMoviesRepository {
    suspend fun getTopRatedMovies(): Flow<PagingData<TopRatedMovie>>
}