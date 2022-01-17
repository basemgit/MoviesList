package com.basemibrahim.movieslist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.basemibrahim.movieslist.data.Repository
import com.basemibrahim.movieslist.data.model.api.movie.MoviesResponse
import com.basemibrahim.movieslist.data.model.api.reviews.ReviewsResponse
import com.basemibrahim.movieslist.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor
    (
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private val _response: MutableLiveData<NetworkResult<MoviesResponse>> = MutableLiveData()
    val response: LiveData<NetworkResult<MoviesResponse>>  = _response

    private val _reviewsResponse: MutableLiveData<NetworkResult<ReviewsResponse>> = MutableLiveData()
    val reviewsResponse: LiveData<NetworkResult<ReviewsResponse>>  = _reviewsResponse


    private val _responseDB: MutableLiveData<MoviesResponse> = MutableLiveData()
    val responseDB: LiveData<MoviesResponse>  = _responseDB


    fun getNowPlayingMovies(page: Int) = viewModelScope.launch {
        repository.getNowplayingMovies(page).collect { values ->
            _response.value = values
        }
    }

    fun getReviews(movieId: Int, page: Int) = viewModelScope.launch {
        repository.getReviews(movieId, page).collect { values ->
            _reviewsResponse.value = values
        }
    }


    fun saveMoviesResponseToDb(response: MoviesResponse) = viewModelScope.launch {
        repository.saveMoviesResponse(response)
    }


    fun fetchResponseFromDb() = viewModelScope.launch {
        repository.getResponseFromDb().collect { values ->
            _responseDB.value = values
        }
    }




}