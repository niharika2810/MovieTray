package com.tmdb.movietray.movies.home.popular.data.apihelper


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tmdb.movietray.movies.common.data.source.local.db.MovieDatabase
import com.tmdb.movietray.movies.home.popular.data.local.entity.PopularMovie
import com.tmdb.movietray.movies.home.popular.data.remote.PopularMoviesRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Niharika.Arora
 */
@Singleton
class IPopularMoviesRepository @Inject constructor(
    private val database: MovieDatabase,
    private val popularMoviesRemoteMediator: PopularMoviesRemoteMediator
) : PopularMoviesRepository {

    override suspend fun getPopularMovies(): Flow<PagingData<PopularMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = popularMoviesRemoteMediator,
            pagingSourceFactory = { database.popularMovieDao().getMovies() }
        ).flow
    }
}