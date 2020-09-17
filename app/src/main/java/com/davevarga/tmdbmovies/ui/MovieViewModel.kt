package com.davevarga.tmdbmovies.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.davevarga.tmdbmovies.db.AppDatabase
import com.davevarga.tmdbmovies.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MovieRepository =
        MovieRepository(AppDatabase.getInstance(application).movieDao())

    val movieList = repository.getMovieList()


    init {
        insert()
    }

    fun insert() {
        viewModelScope.launch {
            repository.insertMovieList()
        }

    }

}