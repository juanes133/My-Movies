package com.example.mymovies

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mymovies.databinding.ActivityMainBinding
import com.example.mymovies.model.Movie
import com.example.mymovies.view.MoviesAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val moviesAdapter = MoviesAdapter(emptyList()) { navigateTo(it) }
        binding.recyclerMovies.adapter = moviesAdapter

        lifecycleScope.launch {
            val apikey = getString(R.string.api_key)
            val popularMovies = MovieDbClient.service.listPopularMovies(apikey)
            moviesAdapter.movies = popularMovies.results
            moviesAdapter.notifyDataSetChanged()
        }
    }

    private fun navigateTo(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie)
        startActivity(intent)
    }
}