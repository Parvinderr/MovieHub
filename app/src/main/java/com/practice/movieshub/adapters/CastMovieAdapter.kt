package com.practice.movieshub.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.practice.movieshub.databinding.CastItemBinding
import com.practice.movieshub.models.movieCast.Cast
import com.practice.movieshub.utils.Constants.POSTER_URL

class CastMovieAdapter(private val data: ArrayList<Cast>) :
    RecyclerView.Adapter<CastMovieAdapter.ViewHolder>() {
    private var _binding: CastItemBinding? = null
    private val binding get() = _binding!!

    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(mListener: OnItemClickListener) {
        listener = mListener
    }

    inner class ViewHolder(binding: CastItemBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastMovieAdapter.ViewHolder {
        _binding = CastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CastMovieAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.setIsRecyclable(false)
        Log.d("CAST SIZE", data.size.toString())
        binding.castImage.load("$POSTER_URL${item.profile_path}")
//        Glide.with(holder.itemView.context).load("$POSTER_URL${item.profile_path}")
//            .into(binding.castImage)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}