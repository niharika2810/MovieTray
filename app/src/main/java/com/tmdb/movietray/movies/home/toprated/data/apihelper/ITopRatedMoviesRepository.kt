package com.tmdb.movietray.movies.home.toprated.data.apihelper


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tmdb.movietray.movies.common.data.source.local.db.MovieDatabase
import com.tmdb.movietray.movies.home.toprated.data.local.db.entity.TopRatedMovie
import com.tmdb.movietray.movies.home.toprated.data.remote.TopRatedMoviesRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ITopRatedMoviesRepository @Inject constructor(
    private val database: MovieDatabase,
    private val topRatedMoviesRemoteMediator: TopRatedMoviesRemoteMediator
) : TopRatedMoviesRepository {

    override suspend fun getTopRatedMovies(): Flow<PagingData<TopRatedMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = topRatedMoviesRemoteMediator,
            pagingSourceFactory = { database.topRatedMovieDao().getMovies() }
        ).flow
    }

}