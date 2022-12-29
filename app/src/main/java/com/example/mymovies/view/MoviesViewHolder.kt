package com.example.mymovies.view

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovies.databinding.ViewMovieItemBinding
import com.example.mymovies.model.Movie

class MoviesViewHolder(private val binding: ViewMovieItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        movie: Movie,
        onClickListener: (Movie) -> Unit,
    ) {
        binding.title.text = movie.title
        Glide
            .with(binding.root.context)
            .load("https://image.tmdb.org/t/p/w185/${movie.poster_path}")
            .into(binding.cover)
        itemView.setOnClickListener {
            onClickListener(movie)
        }
    }
}