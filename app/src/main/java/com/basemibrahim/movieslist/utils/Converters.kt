package com.basemibrahim.movieslist.utils

import androidx.room.TypeConverter
import com.basemibrahim.movieslist.data.model.api.movie.Dates
import com.basemibrahim.movieslist.data.model.api.movie.MoviesResponse
import com.basemibrahim.movieslist.data.model.api.movie.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromMoviesResponse(value: MoviesResponse): String {
        val gson = Gson()
        val type = object : TypeToken<MoviesResponse>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toMoviesResponse(value: String): MoviesResponse {
        val gson = Gson()
        val type = object : TypeToken<MoviesResponse>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromDates(value: Dates): String {
        val gson = Gson()
        val type = object : TypeToken<Dates>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toDates(value: String): Dates {
        val gson = Gson()
        val type = object : TypeToken<Dates>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromResults(value: ArrayList<Result>): String {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Result>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toResults(value: String): ArrayList<Result> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Result>>() {}.type
        return gson.fromJson(value, type)
    }

}