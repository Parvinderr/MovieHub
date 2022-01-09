package com.practice.movieshub.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.movieshub.databinding.TrendingItemLayoutBinding
import com.practice.movieshub.models.typeOfMovie.TypeOfMovieModel
import com.practice.movieshub.utils.Constants.POSTER_URL

class TrendingMovieAdapter(private val data: TypeOfMovieModel) :
    RecyclerView.Adapter<TrendingMovieAdapter.ViewHolder>() {
    private var _binding: TrendingItemLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        mListener = listener
    }


    inner class ViewHolder(binding: TrendingItemLayoutBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrendingMovieAdapter.ViewHolder {
        _binding =
            TrendingItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: TrendingMovieAdapter.ViewHolder, position: Int) {
        val item = data.results[position]
        holder.setIsRecyclable(false)
        Glide.with(holder.itemView.context).load("$POSTER_URL${item.poster_path}")
            .into(binding.trendingItemImage)
    }

    override fun getItemCount(): Int {
        return data.results.size
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}