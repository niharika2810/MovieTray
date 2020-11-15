package com.tmdb.movietray.movies.home.toprated.data.apihelper

import com.tmdb.movietray.movies.common.data.entity.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Niharika.Arora
 */
interface TopRatedMovieService {

    @GET("top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): MoviesResponse
}