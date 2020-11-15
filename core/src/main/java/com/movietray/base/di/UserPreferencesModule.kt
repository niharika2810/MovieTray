package com.movietray.base.di

import com.movietray.base.data.store.IUserPreferenceManager
import com.movietray.base.data.store.UserPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * @author Niharika.Arora
 */
@Module
@InstallIn(ApplicationComponent::class)
object UserPreferencesModule {

    @Provides
    fun provideSessionManager(sessionManager: IUserPreferenceManager): UserPreferenceManager {
        return sessionManager
    }
}