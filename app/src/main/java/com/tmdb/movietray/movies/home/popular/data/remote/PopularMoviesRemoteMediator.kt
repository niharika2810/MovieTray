package com.tmdb.movietray.movies.home.popular.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tmdb.movietray.movies.common.data.source.local.db.MovieDatabase
import com.tmdb.movietray.movies.common.extension.mapDataToPopularMovies
import com.tmdb.movietray.movies.home.popular.data.apihelper.PopularMovieService
import com.tmdb.movietray.movies.home.popular.data.local.entity.PopularMovie
import com.tmdb.movietray.movies.home.popular.data.local.entity.PopularMoviesRemoteKeys
import java.io.InvalidObjectException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalPagingApi::class)
class PopularMoviesRemoteMediator @Inject constructor(
    private val service: PopularMovieService,
    private val database: MovieDatabase,
    private val apiKey: String
) : RemoteMediator<Int, PopularMovie>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularMovie>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                    ?: return MediatorResult.Error(InvalidObjectException("Result is empty"))

                val nextKey = remoteKeys.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?: return MediatorResult.Error(InvalidObjectException("Result is empty"))

                val prevKey = remoteKeys.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                prevKey
            }
        }
        try {
            val movieResponse = service.getPopularMovies(apiKey, page)
            val data = movieResponse.mapDataToPopularMovies()

            database.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    database.popularMovieRemoteDao().clearRemoteKeys()
                    database.popularMovieDao().clearMovies()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (data.isEndOfListReached) null else page + 1
                val keys = data.movies.map {
                    it as PopularMovie
                    PopularMoviesRemoteKeys(
                        movieId = it.movieId,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                database.popularMovieRemoteDao().insertAll(keys)
                database.popularMovieDao().insertMovies(data.movies as List<PopularMovie>)
            }
            return MediatorResult.Success(endOfPaginationReached = data.isEndOfListReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PopularMovie>): PopularMoviesRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            database.popularMovieRemoteDao().remoteKeysByMovieId(repo.movieId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PopularMovie>): PopularMoviesRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            database.popularMovieRemoteDao().remoteKeysByMovieId(movie.movieId)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PopularMovie>): PopularMoviesRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieId?.let { id ->
                database.popularMovieRemoteDao().remoteKeysByMovieId(id)
            }
        }
    }
}
