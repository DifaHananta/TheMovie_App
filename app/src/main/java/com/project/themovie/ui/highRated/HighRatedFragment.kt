package com.project.themovie.ui.highRated

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.themovie.R
import com.project.themovie.data.response.ResultsItem
import com.project.themovie.databinding.FragmentHighRatedBinding
import com.project.themovie.ui.detail.DetailFragment

class HighRatedFragment : Fragment() {

    private lateinit var binding: FragmentHighRatedBinding
    private lateinit var viewModel: HighRatedViewModel
    private lateinit var adapter: HighRatedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHighRatedBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HighRatedViewModel::class.java]
        adapter = HighRatedAdapter()

        binding.rvMovie.layoutManager = GridLayoutManager(activity, 3, LinearLayoutManager.VERTICAL, false)
        binding.rvMovie.setHasFixedSize(true)
        binding.rvMovie.adapter = adapter

        setHighRatedMovies()
        getHighRatedMovies()
        detailMovie()

        return  binding.root
    }

    private fun setHighRatedMovies() {
        viewModel.getListHighRated().observe(viewLifecycleOwner) {
            adapter.setList(it)
        }

        adapter.setOnItemClickCallback(object : HighRatedAdapter.OnItemClickCallback {
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

    private fun getHighRatedMovies() {
        viewModel.setHighRatedMovie()
    }

    private fun detailMovie() {

    }

}