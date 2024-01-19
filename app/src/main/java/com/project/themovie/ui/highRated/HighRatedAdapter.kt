package com.project.themovie.ui.highRated

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.themovie.data.response.ResultsItem
import com.project.themovie.data.retrofit.ApiConfig
import com.project.themovie.databinding.ItemMovieBinding

class HighRatedAdapter : RecyclerView.Adapter<HighRatedAdapter.HighRatedViewHolder>() {

    private val list = ArrayList<ResultsItem>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(movie: ArrayList<ResultsItem>) {
        list.clear()
        list.addAll(movie)
        notifyDataSetChanged()
    }

    inner class HighRatedViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultsItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(item)
            }
            binding.apply {
                val baseImageUrl = "https://image.tmdb.org/t/p/w780/"
                val fullImageUrl = Uri.parse(baseImageUrl).buildUpon()
                    .appendEncodedPath(item.posterPath)
                    .build()

                Glide.with(itemView)
                    .load(fullImageUrl)
                    .dontAnimate()
                    .into(ivItemPhoto)
                tvTitle.text = item.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighRatedViewHolder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HighRatedViewHolder(view)
    }

    override fun getItemCount(): Int {
       return  list.size
    }

    override fun onBindViewHolder(holder: HighRatedViewHolder, position: Int) {
        holder.bind(list[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResultsItem)
    }

}