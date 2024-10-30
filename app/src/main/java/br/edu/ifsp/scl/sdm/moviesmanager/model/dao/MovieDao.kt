package br.edu.ifsp.scl.sdm.moviesmanager.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifsp.scl.sdm.moviesmanager.model.entity.Movie

@Dao
interface MovieDao {
    companion object {
        const val MOVIE_TABLE = "movie"
    }

    @Insert
    fun insertMovie(movie: Movie)

    @Query("SELECT * FROM $MOVIE_TABLE")
    fun retrieveMovies(): List<Movie>

    @Update
    fun updateMovie(movie: Movie)

    @Delete
    fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM $MOVIE_TABLE WHERE genre = :genre")
    fun retrieveMoviesByGenre(genre: String): List<Movie>
}
