package com.basemibrahim.movieslist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.basemibrahim.movieslist.data.Repository
import com.basemibrahim.movieslist.data.model.api.MoviesResponse
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

    fun getNowPlayingMovies(page: Int) = viewModelScope.launch {
        repository.getNowplayingMovies(page).collect { values ->
            _response.value = values
        }
    }





}