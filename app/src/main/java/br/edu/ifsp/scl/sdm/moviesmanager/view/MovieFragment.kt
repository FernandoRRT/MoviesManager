package br.edu.ifsp.scl.sdm.moviesmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.edu.ifsp.scl.sdm.moviesmanager.R
import br.edu.ifsp.scl.sdm.moviesmanager.databinding.FragmentMovieBinding
import br.edu.ifsp.scl.sdm.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.sdm.moviesmanager.model.entity.Movie.Companion.MOVIE_DONE_FALSE
import br.edu.ifsp.scl.sdm.moviesmanager.model.entity.Movie.Companion.MOVIE_DONE_TRUE
import br.edu.ifsp.scl.sdm.moviesmanager.view.MainFragment.Companion.EXTRA_MOVIE
import br.edu.ifsp.scl.sdm.moviesmanager.view.MainFragment.Companion.MOVIE_FRAGMENT_REQUEST_KEY

class MovieFragment : Fragment() {
    private lateinit var ftb: FragmentMovieBinding
    private val navigationArgs: MovieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle =
            getString(R.string.movie_details)

        ftb = FragmentMovieBinding.inflate(inflater, container, false)

        val receivedMovie = navigationArgs.movie
        receivedMovie?.also { movie ->
            with(ftb) {
                nameEt.setText(movie.name)
                yearEt.setText(movie.year.toString())
                durationEt.setText(movie.duration?.toString())
                studioEt.setText(movie.studio)
                genreSpinner.setSelection(
                    resources.getStringArray(R.array.genres_array).indexOf(movie.genre)
                )
                ratingEt.setText(movie.rating?.toString())
                watchedCb.isChecked = movie.watched == MOVIE_DONE_TRUE

                navigationArgs.editMovie.also { editMovie ->
                    nameEt.isEnabled = editMovie
                    yearEt.isEnabled = editMovie
                    durationEt.isEnabled = editMovie
                    studioEt.isEnabled = editMovie
                    genreSpinner.isEnabled = editMovie
                    ratingEt.isEnabled = editMovie
                    watchedCb.isEnabled = editMovie
                    saveBt.visibility = if (editMovie) VISIBLE else GONE
                }
            }
        }

        ftb.run {
            saveBt.setOnClickListener {

                // Verificação se name está vazio
                val name = nameEt.text.toString().trim()
                if (name.isEmpty()) {
                    nameEt.error = getString(R.string.movie_name_cannot_be_empty)
                    nameEt.requestFocus() // Focar no campo name
                    return@setOnClickListener
                }

                // Verificação se year está vazio
                val yearText = yearEt.text.toString().trim()
                if (yearText.isEmpty()) {
                    yearEt.error = getString(R.string.release_year_cannot_be_empty)
                    yearEt.requestFocus() // Focar no campo year
                    return@setOnClickListener
                }
                val year = yearText.toInt()

                // Verificação se o checkbox watched está selecionado para atribuir rating


                val ratingText = ratingEt.text.toString().trim()
                if (ratingText.isNotEmpty()) {
                    if (!watchedCb.isChecked) {
                        ratingEt.error = getString(R.string.watch_the_movie_to_rate_it)
                        ratingEt.requestFocus()
                        return@setOnClickListener
                    } else {
                        if (ratingText.toInt() !in 0..10) {
                            ratingEt.error = getString(R.string.rate_the_movie_from_0_to_10)
                            ratingEt.requestFocus()
                            return@setOnClickListener
                        }
                    }
                }
                val rating = ratingText.toInt()

                // Verificações para outros campos opcionais
                val duration = durationEt.text.toString().takeIf { it.isNotEmpty() }?.toInt()
                val studio = studioEt.text.toString().takeIf { it.isNotEmpty() }
                val genre = genreSpinner.selectedItem.toString()

                // Envia os dados do filme
                setFragmentResult(MOVIE_FRAGMENT_REQUEST_KEY, Bundle().apply {
                    putParcelable(
                        EXTRA_MOVIE, Movie(
                            name = name,
                            year = year,
                            duration = duration,
                            studio = studio,
                            genre = genre,
                            rating = rating,
                            watched = if (watchedCb.isChecked) MOVIE_DONE_TRUE else MOVIE_DONE_FALSE
                        )
                    )
                })
                findNavController().navigateUp()
            }
        }

        return ftb.root
    }
}