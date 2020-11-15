package com.tmdb.movietray.movies.common.data.source.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.movietray.base.utils.typeconverter.TypeConverter
import com.tmdb.movietray.movies.home.popular.data.local.db.dao.PopularMovieDao
import com.tmdb.movietray.movies.home.popular.data.local.db.dao.PopularMovieRemoteDao
import com.tmdb.movietray.movies.home.popular.data.local.entity.PopularMovie
import com.tmdb.movietray.movies.home.popular.data.local.entity.PopularMoviesRemoteKeys
import com.tmdb.movietray.movies.home.toprated.data.local.db.dao.TopRatedMovieDao
import com.tmdb.movietray.movies.home.toprated.data.local.db.dao.TopRatedMovieRemoteDao
import com.tmdb.movietray.movies.home.toprated.data.local.db.entity.TopRatedMovie
import com.tmdb.movietray.movies.home.toprated.data.local.db.entity.TopRatedMoviesRemoteKeys


@Database(
    entities = [PopularMovie::class, PopularMoviesRemoteKeys::class, TopRatedMovie::class, TopRatedMoviesRemoteKeys::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun popularMovieDao(): PopularMovieDao
    abstract fun popularMovieRemoteDao(): PopularMovieRemoteDao
    abstract fun topRatedMovieDao(): TopRatedMovieDao
    abstract fun topRatedMovieRemoteDao(): TopRatedMovieRemoteDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        ).also {
                            INSTANCE = it
                        }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java, "MovieDatabase.db"
            )
                .build()
    }

}