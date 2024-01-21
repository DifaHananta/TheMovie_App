package com.project.themovie.ui.favorite

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.themovie.R
import com.project.themovie.data.local.FavoriteMovie
import com.project.themovie.data.response.ResultsItem
import com.project.themovie.databinding.FragmentFavoriteBinding
import com.project.themovie.ui.adapter.MovieListAdapter
import com.project.themovie.ui.detail.DetailFragment
import com.project.themovie.ui.detail.DetailViewModel

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]
        adapter = MovieListAdapter()

        val layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            GridLayoutManager(activity, 3, LinearLayoutManager.VERTICAL, false)
        } else {
            GridLayoutManager(activity, 5, LinearLayoutManager.VERTICAL, false)
        }
        binding.rvMovie.layoutManager = layoutManager
        binding.rvMovie.adapter = adapter

        getFavoriteMovies()
        showLoading(true)

        return binding.root
    }

    private fun getFavoriteMovies() {
        viewModel.getFavorite()?.observe(viewLifecycleOwner) {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
                showEmpty(list.isEmpty())
            } else {
                showEmpty(true)
            }
            showLoading(false)
        }

        adapter.setOnItemClickCallback(object : MovieListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResultsItem) {
                val bundle = Bundle()
                bundle.putInt(DetailFragment.EXTRA_ID, data.id)
                val detailFragment = DetailFragment()
                detailFragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
    }

    private fun mapList(favorites: List<FavoriteMovie>): ArrayList<ResultsItem> {
        val favoriteList = ArrayList<ResultsItem>()
        for (favorite in favorites) {
            val userMapped = ResultsItem(
                favorite.id,
                "",
                favorite.poster
            )
            favoriteList.add(userMapped)
        }
        return favoriteList
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showEmpty(state: Boolean) {
        binding.tvEmpty.visibility = if (state) View.VISIBLE else View.GONE
    }

}