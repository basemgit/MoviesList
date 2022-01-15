package com.basemibrahim.movieslist.di

import android.content.Context
import androidx.room.Room
import com.basemibrahim.movieslist.data.local.db.AppDatabase
import com.basemibrahim.movieslist.data.local.db.dao.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideLogDao(database: AppDatabase): MoviesDao {
        return database.moviesDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "MoviesList.db"
        ).build()
    }
}