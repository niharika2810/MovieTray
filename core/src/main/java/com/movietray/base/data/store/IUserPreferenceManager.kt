package com.movietray.base.data.store

import androidx.datastore.preferences.preferencesKey
import com.movietray.base.data.local.DataStoreProvider.getValue
import com.movietray.base.data.local.DataStoreProvider.setValue
import com.movietray.base.data.store.IUserPreferenceManager.Keys.USER_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author Niharika
 **/
class IUserPreferenceManager @Inject constructor() : UserPreferenceManager {

    override suspend fun setUserName(name: String) {
        setValue(USER_NAME, name)
    }

    override suspend fun getUserUserName(): Flow<String?> {
        return getValue(USER_NAME).map { it }
    }

    object Keys {
        val USER_NAME = preferencesKey<String>("user_name")
    }
}