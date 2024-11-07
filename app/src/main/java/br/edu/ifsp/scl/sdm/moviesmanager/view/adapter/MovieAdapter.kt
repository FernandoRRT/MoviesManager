package br.edu.ifsp.scl.sdm.moviesmanager.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.moviesmanager.R
import br.edu.ifsp.scl.sdm.moviesmanager.databinding.TileMovieBinding
import br.edu.ifsp.scl.sdm.moviesmanager.model.entity.Movie

class MovieAdapter(
    private val movieList: List<Movie>,
    private val onMovieClickListener: OnMovieClickListener
): RecyclerView.Adapter<MovieAdapter.MovieTileViewHolder>() {
    inner class MovieTileViewHolder(tileMovieBinding: TileMovieBinding) :
        RecyclerView.ViewHolder(tileMovieBinding.root) {
        val nameTv: TextView = tileMovieBinding.nameTv
        val yearTv: TextView = tileMovieBinding.yearTv

        init {
            tileMovieBinding.apply {
                root.run {
                    setOnCreateContextMenuListener { menu, _, _ ->
                        (onMovieClickListener as? Fragment)?.activity?.menuInflater?.inflate(
                            R.menu.context_menu_movie,
                            menu
                        )
                        menu?.findItem(R.id.deleteMovieCm)?.setOnMenuItemClickListener {
                            onMovieClickListener.onRemoveMovieMenuItemClick(adapterPosition)
                            true
                        }
                        menu?.findItem(R.id.editMovieCm)?.setOnMenuItemClickListener {
                            onMovieClickListener.onEditMovieMenuItemClick(adapterPosition)
                            true
                        }
                    }
                    setOnClickListener {
                        onMovieClickListener.onMovieClick(adapterPosition)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TileMovieBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ).run { MovieTileViewHolder(this) }

    override fun onBindViewHolder(holder: MovieTileViewHolder, position: Int) {
        movieList[position].let { movie ->
            with(holder) {
                nameTv.text = movie.name
                yearTv.text = movie.year.toString()
            }
        }
    }

    override fun getItemCount() = movieList.size
}