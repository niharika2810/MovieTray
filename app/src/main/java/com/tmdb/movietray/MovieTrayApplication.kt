package com.tmdb.movietray

import android.app.Application
import com.movietray.base.data.local.DataStoreProvider
import dagger.hilt.android.HiltAndroidApp

/**
 * @author Niharika.Arora
 */
@HiltAndroidApp
class MovieTrayApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DataStoreProvider.init(this)
    }
}
