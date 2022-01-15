package com.basemibrahim.movieslist.data.local.db.dao;

import androidx.room.*
import com.basemibrahim.movieslist.data.model.api.movie.MoviesResponse


@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertResponse(item: MoviesResponse)

    @Query("select * from moviesresponse")
    fun getResponse() : MoviesResponse
}
