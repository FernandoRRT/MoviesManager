package br.edu.ifsp.scl.sdm.moviesmanager.view

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.sdm.moviesmanager.R
import br.edu.ifsp.scl.sdm.moviesmanager.controller.MovieViewModel
import br.edu.ifsp.scl.sdm.moviesmanager.databinding.FragmentMainBinding
import br.edu.ifsp.scl.sdm.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.sdm.moviesmanager.view.adapter.MovieAdapter
import br.edu.ifsp.scl.sdm.moviesmanager.view.adapter.OnMovieClickListener

class MainFragment : Fragment(), OnMovieClickListener {
    private lateinit var fmb: FragmentMainBinding

    // Data source
    private val movieList: MutableList<Movie> = mutableListOf()

    // Adapter
    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter(movieList, this)
    }

    // Navigation controller
    private val navController: NavController by lazy {
        findNavController()
    }

    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModel.MovieViewModelFactory
    }

    // Communication constants
    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
        const val MOVIE_FRAGMENT_REQUEST_KEY = "MOVIE_FRAGMENT_REQUEST_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        setFragmentResultListener(MOVIE_FRAGMENT_REQUEST_KEY) { requestKey, bundle ->
            if (requestKey == MOVIE_FRAGMENT_REQUEST_KEY) {
                val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(EXTRA_MOVIE, Movie::class.java)
                } else {
                    bundle.getParcelable(EXTRA_MOVIE)
                }
                movie?.also { receivedMovie ->
                    // Obter a posição do filme na lista, se ele já existir
                    val position = movieList.indexOfFirst { it.name == receivedMovie.name }

                    // Se a posição for diferente de -1, então o item já existe na lista
                    if (position != -1) {
                        movieViewModel.editMovie(receivedMovie)
                        movieList[position] = receivedMovie
                        movieAdapter.notifyItemChanged(position)
                    } else {
                        // Se a posição for igual a -1, então o item não existe na lista
                        movieViewModel.insertMovie(receivedMovie)
                        movieList.add(receivedMovie)
                        movieAdapter.notifyItemInserted(movieList.lastIndex)
                    }
                }


                // Hiding soft keyboard
                (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    fmb.root.windowToken,
                    HIDE_NOT_ALWAYS
                )
            }
        }

//Eu preciso passar um onwer para o observer, então eu passei o lifecycleOwner
//E preciso passar um observer, então eu passei uma lambda que recebe uma lista de movies
//Se ela for notificada, ela vai chamar a função updateMovieList
        movieViewModel.moviesMld.observe(requireActivity()) { movies ->
            movieList.clear()
            movies.forEachIndexed { index, movie ->
                movieList.add(movie)
                movieAdapter.notifyItemChanged(index)
            }
        }

        movieViewModel.getMovies()
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu) // Inflando o sort_menu para o MainFragment
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.orderByNameMi -> {
                orderMoviesByName()
                true
            }
            R.id.orderByRatingMi -> {
                orderMoviesByRating()
                true
            }
            else -> false
        }
    }

    private fun orderMoviesByName() {
        movieList.sortBy { it.name }
        movieAdapter.notifyDataSetChanged()
    }

    private fun orderMoviesByRating() {
        movieList.sortByDescending { it.rating }
        movieAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle =
            getString(R.string.movie_list)

        fmb = FragmentMainBinding.inflate(inflater, container, false).apply {
            moviesRv.layoutManager = LinearLayoutManager(context)
            moviesRv.adapter = movieAdapter

            addMovieFab.setOnClickListener {
                navController.navigate(
                    MainFragmentDirections.actionMainFragmentToMovieFragment(null, editMovie = false)
                )
            }
        }

        return fmb.root
    }

    override fun onMovieClick(position: Int) = navigateToMovieFragment(position, false)

    override fun onRemoveMovieMenuItemClick(position: Int) {
        movieViewModel.removeMovie(movieList[position])
        movieList.removeAt(position)
        movieAdapter.notifyItemRemoved(position)
    }

    override fun onEditMovieMenuItemClick(position: Int) = navigateToMovieFragment(position, true)

    private fun navigateToMovieFragment(position: Int, editMovie: Boolean) {
        movieList[position].also {
            navController.navigate(
                MainFragmentDirections.actionMainFragmentToMovieFragment(it, editMovie)
            )
        }
    }
}