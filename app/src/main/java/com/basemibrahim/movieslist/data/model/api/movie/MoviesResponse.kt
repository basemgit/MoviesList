package com.basemibrahim.movieslist.data.model.api.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviesResponse(
    val dates: Dates,
    val page: Int,
    @PrimaryKey
    var results: ArrayList<Result>,
    val total_pages: Int,
    val total_results: Int
)