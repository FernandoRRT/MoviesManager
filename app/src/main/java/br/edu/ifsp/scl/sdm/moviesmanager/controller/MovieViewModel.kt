package br.edu.ifsp.scl.sdm.moviesmanager.controller

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.room.Room
import br.edu.ifsp.scl.sdm.moviesmanager.model.database.MovieDatabase
import br.edu.ifsp.scl.sdm.moviesmanager.model.entity.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application): ViewModel() {
    private val movieDaoImpl = Room.databaseBuilder(
        application.applicationContext,
        MovieDatabase::class.java,
        MovieDatabase.MOVIE_DATABASE
    ).build().getMovieDao()

    //objeto que observará o Fragment para atualizar a lista de filmes
    val moviesMld = MutableLiveData<List<Movie>>()


    fun insertMovie(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.createMovie(movie)
        }
    }

    fun getMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            val movies = movieDaoImpl.retrieveMovies()
            moviesMld.postValue(movies)
        }
    }

    fun editMovie(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.updateMovie(movie)
        }
    }

    fun removeMovie(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.deleteMovie(movie)
        }
    }

    companion object {
        val MovieViewModelFactory = object: ViewModelProvider.Factory {
            override fun <T: ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
                MovieViewModel(checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])) as T
        }
    }
}
