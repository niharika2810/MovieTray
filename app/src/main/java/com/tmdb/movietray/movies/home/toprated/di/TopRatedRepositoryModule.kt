package com.tmdb.movietray.movies.home.toprated.di

import com.tmdb.movietray.movies.home.toprated.data.apihelper.ITopRatedMoviesRepository
import com.tmdb.movietray.movies.home.toprated.data.apihelper.TopRatedMoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * @author Niharika.Arora
 */
@Module
@InstallIn(ActivityComponent::class)
class TopRatedRepositoryModule {

    @Provides
    fun provideTopRatedMoviesRepository(repository: ITopRatedMoviesRepository): TopRatedMoviesRepository {
        return repository
    }
}