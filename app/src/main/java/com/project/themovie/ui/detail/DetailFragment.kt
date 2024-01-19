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
import com.project.themovie.R
import com.project.themovie.databinding.FragmentDetailBinding
import com.project.themovie.ui.highRated.HighRatedViewModel

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

        movieDetail()

        return binding.root
    }

    private fun movieDetail() {

        val movieId = arguments?.getInt(EXTRA_ID)
        if (movieId != null) {
            viewModel.setMovieDetail(movieId.toString())
        } else {
            Log.e(TAG, "MovieId is null")
        }

        viewModel.getMovieDetail().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    val baseImageUrl = "https://image.tmdb.org/t/p/w780/"
                    val posterUrl = Uri.parse(baseImageUrl).buildUpon()
                        .appendEncodedPath(it.posterPath)
                        .build()
                    val coverUrl = Uri.parse(baseImageUrl).buildUpon()
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
            }
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}