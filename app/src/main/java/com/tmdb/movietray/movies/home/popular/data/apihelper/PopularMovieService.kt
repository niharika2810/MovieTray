package com.tmdb.movietray.movies.home.popular.data.apihelper

import com.tmdb.movietray.movies.common.data.entity.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Niharika.Arora
 */
interface PopularMovieService {

    @GET("popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): MoviesResponse
}