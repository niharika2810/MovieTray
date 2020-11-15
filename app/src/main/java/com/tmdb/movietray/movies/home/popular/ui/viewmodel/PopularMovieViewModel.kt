package com.tmdb.movietray.movies.home.popular.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tmdb.movietray.movies.home.popular.data.apihelper.PopularMoviesRepository
import com.tmdb.movietray.movies.home.popular.data.local.entity.PopularMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * @author Niharika.Arora
 */
class PopularMovieViewModel @ViewModelInject constructor(
    private val repository: PopularMoviesRepository
) : ViewModel() {

    suspend fun getPopularMovies(): Flow<PagingData<PopularMovie>> {
        return repository
            .getPopularMovies()
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
    }
}