package br.edu.ifsp.scl.sdm.moviesmanager.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.edu.ifsp.scl.sdm.moviesmanager.model.dao.MovieDao
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = MovieDao.MOVIE_TABLE)
data class Movie(
    @PrimaryKey
    var name: String, // Nome único do filme
    var year: Int? = null, // Ano de lançamento do filme
    var duration: Int? = null, // Tempo de duração em minutos
    var studio: String? = null, // Produtora ou estúdio
    var genre: String, // Gênero do filme
    var rating: Int? = null, // Nota do usuário (0-10)
    var watched: Int = MOVIE_DONE_FALSE // Flag para indicar se foi assistido
) : Parcelable {
    companion object {
        const val MOVIE_DONE_TRUE = 1
        const val MOVIE_DONE_FALSE = 0
    }
}