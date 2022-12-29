package com.example.mymovies

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovies.databinding.ViewMovieItemBinding

class MoviesViewHolder(private val binding: ViewMovieItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        movie: Movie,
        onClickListener: (Movie) -> Unit,
    ) {
        binding.title.text = movie.title
        Glide.with(binding.root.context)
            .load(movie.cover).into(binding.cover)
        itemView.setOnClickListener {
            onClickListener(movie)
        }
    }
}