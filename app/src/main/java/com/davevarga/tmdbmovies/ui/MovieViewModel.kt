package com.davevarga.tmdbmovies.ui

import android.app.Application
import androidx.lifecycle.*
import com.davevarga.tmdbmovies.db.AppDatabase
import com.davevarga.tmdbmovies.models.Movie
import com.davevarga.tmdbmovies.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(application: Application, minYear: String, maxYear: String) :
    AndroidViewModel(application) {

    private val repository: MovieRepository =
        MovieRepository(AppDatabase.getInstance(application).movieDao())

    private val minimumYear = minYear
    private val maximumYear = maxYear
    var movieList: LiveData<List<Movie>>


    init {
        insert(minimumYear, maximumYear)
        movieList = repository.getMovieList()
    }

    fun insert(minYear: String, maxYear: String) {
        viewModelScope.launch {
            repository.insertMovieList(minYear, maxYear)
        }

    }

    fun delete() {
        viewModelScope.launch {
            repository.deleteAll()
        }

    }
}


@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(
    val application: Application,
    val minYear: String,
    val maxYear: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(application, minYear, maxYear) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}