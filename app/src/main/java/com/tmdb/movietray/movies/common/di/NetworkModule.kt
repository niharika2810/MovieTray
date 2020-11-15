package com.tmdb.movietray.movies.common.di

import com.tmdb.movietray.movies.home.popular.data.apihelper.PopularMovieService
import com.tmdb.movietray.movies.home.toprated.data.apihelper.TopRatedMovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author Niharika.Arora
 */
@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providePopularMoviesService(retrofit: Retrofit): PopularMovieService =
        retrofit.create(PopularMovieService::class.java)

    @Provides
    @Singleton
    fun provideTopRatedMoviesService(retrofit: Retrofit): TopRatedMovieService =
        retrofit.create(TopRatedMovieService::class.java)
}