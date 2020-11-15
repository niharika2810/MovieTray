package com.tmdb.movietray.movies.home.toprated.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tmdb.movietray.movies.common.data.source.local.db.MovieDatabase
import com.tmdb.movietray.movies.common.extension.mapDataToTopRatedMovies
import com.tmdb.movietray.movies.home.toprated.data.apihelper.TopRatedMovieService
import com.tmdb.movietray.movies.home.toprated.data.local.db.entity.TopRatedMovie
import com.tmdb.movietray.movies.home.toprated.data.local.db.entity.TopRatedMoviesRemoteKeys
import java.io.InvalidObjectException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalPagingApi::class)
class TopRatedMoviesRemoteMediator @Inject constructor(
    private val service: TopRatedMovieService,
    private val database: MovieDatabase,
    private val apiKey: String
) : RemoteMediator<Int, TopRatedMovie>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopRatedMovie>
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
            val movieResponse = service.getTopRatedMovies(apiKey, page)
            val data = movieResponse.mapDataToTopRatedMovies()

            database.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    database.topRatedMovieRemoteDao().clearRemoteKeys()
                    database.topRatedMovieDao().clearMovies()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (data.isEndOfListReached) null else page + 1
                val keys = data.movies.map {
                    it as TopRatedMovie
                    TopRatedMoviesRemoteKeys(
                        movieId = it.movieId,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                database.topRatedMovieRemoteDao().insertAll(keys)
                database.topRatedMovieDao().insertMovies(data.movies as List<TopRatedMovie>)
            }
            return MediatorResult.Success(endOfPaginationReached = data.isEndOfListReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, TopRatedMovie>): TopRatedMoviesRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            database.topRatedMovieRemoteDao().remoteKeysByMovieId(repo.movieId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, TopRatedMovie>): TopRatedMoviesRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            database.topRatedMovieRemoteDao().remoteKeysByMovieId(movie.movieId)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, TopRatedMovie>): TopRatedMoviesRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieId?.let { id ->
                database.topRatedMovieRemoteDao().remoteKeysByMovieId(id)
            }
        }
    }
}
