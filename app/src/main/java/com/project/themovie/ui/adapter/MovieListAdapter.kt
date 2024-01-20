package com.project.themovie.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.themovie.BuildConfig
import com.project.themovie.data.response.ResultsItem
import com.project.themovie.databinding.ItemMovieBinding

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    private val list = ArrayList<ResultsItem>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(movie: ArrayList<ResultsItem>) {
        list.clear()
        list.addAll(movie)
        list.isEmpty()
        notifyDataSetChanged()
    }

    inner class MovieListViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultsItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(item)
            }
            binding.apply {
                val baseUrlImage = BuildConfig.BASE_URL_IMAGE
                val posterUrl = Uri.parse(baseUrlImage).buildUpon()
                    .appendEncodedPath(item.posterPath)
                    .build()

                Glide.with(itemView)
                    .load(posterUrl)
                    .dontAnimate()
                    .into(ivItemPhoto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieListViewHolder(view)
    }

    override fun getItemCount(): Int {
       return  list.size
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResultsItem)
    }

}