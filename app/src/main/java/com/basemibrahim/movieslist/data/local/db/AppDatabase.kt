
package com.basemibrahim.movieslist.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.basemibrahim.movieslist.data.local.db.dao.MoviesDao
import com.basemibrahim.movieslist.data.model.api.movie.MoviesResponse
import com.basemibrahim.movieslist.utils.Converters

@Database(entities = arrayOf(MoviesResponse::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}
