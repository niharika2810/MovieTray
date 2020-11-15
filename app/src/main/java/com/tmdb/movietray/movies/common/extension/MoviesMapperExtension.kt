package com.tmdb.movietray.movies.common.extension

import com.tmdb.movietray.movies.common.data.entity.Movies
import com.tmdb.movietray.movies.common.data.entity.MoviesResponse
import com.tmdb.movietray.movies.home.popular.data.local.entity.PopularMovie
import com.tmdb.movietray.movies.home.toprated.data.local.db.entity.TopRatedMovie

//todo change some fields in popular and top rated - Put in Feature module
fun MoviesResponse.mapDataToPopularMovies(): Movies =
    with(this) {
        Movies(
            total = total,
            page = page,
            movies = results.map {
                PopularMovie(
                    it.id,
                    it.posterPath,
                    it.title,
                    it.overview,
                    it.voteAverage
                )
            }
        )
    }


fun MoviesResponse.mapDataToTopRatedMovies(): Movies =
    with(this) {
        Movies(
            total = total,
            page = page,
            movies = results.map {
                TopRatedMovie(
                    it.id,
                    it.posterPath,
                    it.title,
                    it.overview,
                    it.voteAverage
                )
            }
        )
    }