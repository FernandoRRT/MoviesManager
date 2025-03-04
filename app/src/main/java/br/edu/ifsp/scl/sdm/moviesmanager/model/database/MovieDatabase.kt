package br.edu.ifsp.scl.sdm.moviesmanager.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.edu.ifsp.scl.sdm.moviesmanager.model.dao.MovieDao
import br.edu.ifsp.scl.sdm.moviesmanager.model.entity.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    companion object {
        const val MOVIE_DATABASE = "movieDatabase"
    }

    abstract fun getMovieDao(): MovieDao
}