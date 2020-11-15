package com.movietray.base.data.store

import kotlinx.coroutines.flow.Flow

/**
 * Created by Niharika
 **/
interface UserPreferenceManager {
    suspend fun setUserName(name: String)

    suspend fun getUserUserName(): Flow<String?>
}