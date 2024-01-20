package com.project.themovie.ui.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.themovie.R
import com.project.themovie.data.response.ResultsItem
import com.project.themovie.databinding.FragmentPopularBinding
import com.project.themovie.ui.adapter.MovieListAdapter
import com.project.themovie.ui.detail.DetailFragment

class PopularFragment : Fragment() {

    private lateinit var binding: FragmentPopularBinding
    private lateinit var viewModel: PopularViewModel
    private lateinit var adapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(inflater, container, false)

        viewModel = PopularViewModel()
        adapter = MovieListAdapter()

        binding.rvMovie.layoutManager = GridLayoutManager(activity, 3, LinearLayoutManager.VERTICAL, false)
        binding.rvMovie.setHasFixedSize(true)
        binding.rvMovie.adapter = adapter

        setPopularMovies()
        getPopularMovies()
        showLoading(true)

        return binding.root
    }

    private fun setPopularMovies() {
        viewModel.setPopularMovie()
    }

    private fun getPopularMovies() {
        viewModel.getPopularMovie().observe(viewLifecycleOwner) {
            adapter.setList(it)
            showLoading(false)
        }

        adapter.setOnItemClickCallback(object : MovieListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResultsItem) {
                val bundle = Bundle()
                bundle.putInt(DetailFragment.EXTRA_ID, data.id)
                bundle.putString(DetailFragment.EXTRA_TITLE, data.title)
                bundle.putString(DetailFragment.EXTRA_POSTER, data.posterPath)
                val detailFragment = DetailFragment()
                detailFragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

}