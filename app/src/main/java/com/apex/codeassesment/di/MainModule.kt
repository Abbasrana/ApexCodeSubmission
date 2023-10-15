package com.apex.codeassesment.di

import android.content.Context
import android.content.SharedPreferences
import com.apex.codeassesment.data.IUserRepository
import com.apex.codeassesment.data.UserRepository
import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.local.PreferencesManager
import com.apex.codeassesment.data.remote.RemoteDataSource
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides

@Module
object MainModule {

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("random-user-preferences", Context.MODE_PRIVATE)
    }

    @Provides
    fun providePreferencesManager(): PreferencesManager = PreferencesManager()

    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    fun providesLocalDataSource(
        preferencesManager: PreferencesManager,
        moshi: Moshi
    ): LocalDataSource {
        return LocalDataSource(preferencesManager, moshi)
    }

    @Provides
    fun providesUserRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): IUserRepository {
        return UserRepository(localDataSource, remoteDataSource)
    }
}