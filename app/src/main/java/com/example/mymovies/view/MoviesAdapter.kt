package com.example.mymovies.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.databinding.ViewMovieItemBinding
import com.example.mymovies.model.Movie

class MoviesAdapter(
    var movies: List<Movie>,
    private val onClickListener: (Movie) -> Unit,
) :
    RecyclerView.Adapter<MoviesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding =
            ViewMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(movies[position], onClickListener)
    }
    override fun getItemCount(): Int = movies.size
}