package com.tmdb.movietray.movies.common.di

import android.content.Context
import com.tmdb.movietray.movies.common.data.source.local.db.MovieDatabase
import com.tmdb.movietray.movies.home.popular.data.apihelper.PopularMovieService
import com.tmdb.movietray.movies.home.toprated.data.apihelper.TopRatedMovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author Niharika.Arora
 */
@Module
@InstallIn(ApplicationComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun providePopularMoviesService(retrofit: Retrofit): PopularMovieService =
        retrofit.create(PopularMovieService::class.java)

    @Provides
    @Singleton
    fun provideTopRatedMoviesService(retrofit: Retrofit): TopRatedMovieService =
        retrofit.create(TopRatedMovieService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return MovieDatabase.getInstance(context)
    }
}