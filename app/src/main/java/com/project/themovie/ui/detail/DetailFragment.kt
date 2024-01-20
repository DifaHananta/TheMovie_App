package com.project.themovie.ui.detail

import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.project.themovie.BuildConfig
import com.project.themovie.R
import com.project.themovie.databinding.FragmentDetailBinding
import com.project.themovie.ui.highRated.HighRatedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]

        getMovieDetail()
        movieFavorite()
        showLoading(true)

        return binding.root
    }

    private fun getMovieDetail() {
        val movieId = arguments?.getInt(EXTRA_ID)
        if (movieId != null) {
            viewModel.setMovieDetail(movieId.toString())
        } else {
            Log.e(TAG, "MovieId is null")
        }

        viewModel.getMovieDetail().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    val baseUrlImage = BuildConfig.BASE_URL_IMAGE
                    val posterUrl = Uri.parse(baseUrlImage).buildUpon()
                        .appendEncodedPath(it.posterPath)
                        .build()
                    val coverUrl = Uri.parse(baseUrlImage).buildUpon()
                        .appendEncodedPath(it.backdropPath)
                        .build()

                    Glide.with(ivCover)
                        .load(coverUrl)
                        .dontAnimate()
                        .into(ivCover)
                    Glide.with(ivPoster)
                        .load(posterUrl)
                        .dontAnimate()
                        .into(ivPoster)
                    tvTitle.text = it.title

                    val formattedVoteAverage = String.format("%.1f", it.voteAverage)
                    tvRating.text = formattedVoteAverage

                    tvReleaseDate.text = it.releaseDate
                    tvOverview.text = it.overview
                }
                showLoading(false)
            }
        }
    }

    private fun movieFavorite() {

        val movieId = arguments?.getInt(EXTRA_ID)
        val movieTitle = arguments?.getString(EXTRA_TITLE)
        val moviePoster = arguments?.getString(EXTRA_POSTER)
        var isChecked = false

        CoroutineScope(Dispatchers.IO).launch {
            val count = movieId?.let { viewModel.checkFavorite(it) }
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.btnFavorite.isChecked = true
                        isChecked = true
                    } else {
                        binding.btnFavorite.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.btnFavorite.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                if (movieId != null) {
                    viewModel.addFavorite(movieId, movieTitle.toString(), moviePoster.toString())
                }
            } else {
                if (movieId != null) {
                    viewModel.removeFavorite(movieId)
                }
            }
            binding.btnFavorite.isChecked = isChecked
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_POSTER = "extra_poster"
    }
}