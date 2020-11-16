package com.tmdb.movietray.movies.common.data.source.local.store

import kotlinx.coroutines.flow.Flow

/**
 * Created by Niharika
 **/
interface UserPreferenceManager {
    suspend fun setUserName(name: String)

    suspend fun getUserUserName(): Flow<String?>
}