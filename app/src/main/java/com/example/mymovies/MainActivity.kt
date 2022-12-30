package com.example.mymovies

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mymovies.databinding.ActivityMainBinding
import com.example.mymovies.model.Movie
import com.example.mymovies.view.MoviesAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@RequiresApi(Build.VERSION_CODES.M)
class MainActivity : AppCompatActivity() {

    companion object {
        private const val DEFAULT_REGION = "US"
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val moviesAdapter = MoviesAdapter(emptyList()) { navigateTo(it) }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGrated ->
            doRequestPopularMovies(isGrated)
        }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerMovies.adapter = moviesAdapter

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun doRequestPopularMovies(isLocationGranted: Boolean) {
        lifecycleScope.launch {
            val apikey = getString(R.string.api_key)
            val region = getRegion(isLocationGranted)
            val popularMovies = MovieDbClient.service.listPopularMovies(apikey, region)
            moviesAdapter.movies = popularMovies.results
            moviesAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getRegion(isLocationGranted: Boolean): String =
        suspendCancellableCoroutine { continuation ->
            if (isLocationGranted) {
                fusedLocationClient.lastLocation.addOnCompleteListener {
                    continuation.resume(getRegionFromLocation(it.result))
                }
            } else {
                continuation.resume(DEFAULT_REGION)
            }
        }

    private fun getRegionFromLocation(location: Location?): String {
        if (location == null)
            return DEFAULT_REGION
        val geocoder = Geocoder(this)
        val result = geocoder.getFromLocation(
            location.latitude,
            location.longitude,
            1
        )
        return result?.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }

    private fun navigateTo(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie)
        startActivity(intent)
    }
}