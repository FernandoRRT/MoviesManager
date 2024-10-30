package br.edu.ifsp.scl.sdm.moviesmanager.view.adapter

interface OnMovieClickListener {
    fun onMovieClick(position: Int)
    fun onRemoveMovieMenuItemClick(position: Int)
    fun onEditMovieMenuItemClick(position: Int)
    fun onwatchedCheckBoxClick(position: Int, checked: Boolean)
}