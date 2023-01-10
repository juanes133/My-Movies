package com.example.mymovies

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.mymovies.databinding.ActivityDetailBinding
import com.example.mymovies.model.Movie
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "DetailActivity:movie"
    }

    var like4 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        if (movie != null) {
            title = movie.title
            Glide
                .with(this)
                .load("https://image.tmdb.org/t/p/w780/${movie.backdrop_path}")
                .into(binding.backdrop)
            binding.summary.text = movie.overview
            bindDetalInfo(binding.detailInfo, movie)
        }
        setContentView(binding.root)
        like4ImageView.setOnClickListener {
            like4 = likeAnimation(like4ImageView, R.raw.apple_event, like4)
        }
    }

    private fun bindDetalInfo(detailInfo: TextView, movie: Movie) {
        detailInfo.text = buildSpannedString {
            appendInfo(R.string.original_language, movie.original_language)
            appendInfo(R.string.original_title, movie.original_title)
            appendInfo(R.string.release_date, movie.release_date)
            appendInfo(R.string.popularity, movie.popularity.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
        }
    }

    private fun SpannableStringBuilder.appendInfo(stringRes: Int, value: String) {
        bold {
            append(getString(stringRes))
            append(": ")
        }
        appendLine(value)
    }

    private fun likeAnimation(
        imageView: LottieAnimationView,
        animation: Int,
        like: Boolean,
    ): Boolean {
        if (!like) {
            imageView.setAnimation(animation)
            imageView.playAnimation()
        } else {
            imageView.animate()
                .alpha(0f)
                .setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animator: Animator) {

                        imageView.setImageResource(R.mipmap.ic_favorite)
                        imageView.alpha = 1f
                    }

                })
        }
        return !like
    }
}