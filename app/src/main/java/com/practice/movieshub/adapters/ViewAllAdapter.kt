package com.practice.movieshub.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.movieshub.databinding.ViewAllListItemBinding
import com.practice.movieshub.models.typeOfMovie.TypeOfMovieModel
import com.practice.movieshub.utils.Constants.POSTER_URL

class ViewAllAdapter(private val data: TypeOfMovieModel) :
    RecyclerView.Adapter<ViewAllAdapter.ViewHolder>() {
    private var _binding: ViewAllListItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(mListener: OnItemClickListener) {
        listener = mListener
    }

    inner class ViewHolder(binding: ViewAllListItemBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAllAdapter.ViewHolder {
        _binding =
            ViewAllListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewAllAdapter.ViewHolder, position: Int) {
        val item = data.results[position]
        holder.setIsRecyclable(false)
        Glide.with(holder.itemView.context).load("$POSTER_URL${item.poster_path}")
            .into(binding.latestItemImage)
        binding.movieTitle.text = item.title
        "(${item.release_date})".also { binding.releaseDate.text = it }
        binding.movieOverview.text = item.overview
        binding.movieRating.setProgress(item.vote_average.toFloat())

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