package com.tmdb.movietray.movies.home.toprated.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tmdb.movietray.movies.home.toprated.data.apihelper.TopRatedMoviesRepository
import com.tmdb.movietray.movies.home.toprated.data.local.db.entity.TopRatedMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * @author Niharika.Arora
 */
class TopRatedMovieViewModel @ViewModelInject constructor(
    private val repository: TopRatedMoviesRepository
) : ViewModel() {

    suspend fun getTopRatedMovies(): Flow<PagingData<TopRatedMovie>> {
        return repository
            .getTopRatedMovies()
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
    }
}