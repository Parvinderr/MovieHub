package com.practice.movieshub.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.movieshub.databinding.LatestItemLayoutBinding
import com.practice.movieshub.models.typeOfMovie.TypeOfMovieModel
import com.practice.movieshub.utils.Constants.POSTER_URL

class LatestMovieAdapter(private val data: TypeOfMovieModel) :
    RecyclerView.Adapter<LatestMovieAdapter.ViewHolder>() {
    private var _binding: LatestItemLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setClickListener(mListener: OnItemClickListener) {
        listener = mListener
    }

    inner class ViewHolder(binding: LatestItemLayoutBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

        }
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LatestMovieAdapter.ViewHolder {
        _binding =
            LatestItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: LatestMovieAdapter.ViewHolder, position: Int) {
        val item = data.results[position]
        holder.setIsRecyclable(false)
        Log.d("Position", position.toString())
        binding.loadedMovieName.text = item.title
        Glide.with(holder.itemView.context).load("$POSTER_URL${item.backdrop_path}")
            .into(binding.latestItemImage)

    }

    override fun getItemCount(): Int {
        return data.results.size
    }
}