package com.practice.movieshub.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.practice.movieshub.adapters.CastMovieAdapter
import com.practice.movieshub.adapters.LatestMovieAdapter
import com.practice.movieshub.databinding.FragmentDetailsBinding
import com.practice.movieshub.models.movieCast.Cast
import com.practice.movieshub.models.movieCast.MovieCastModel
import com.practice.movieshub.models.movieDetailModel.MovieDetailModel
import com.practice.movieshub.models.typeOfMovie.TypeOfMovieModel
import com.practice.movieshub.network.RetrofitInstance
import com.practice.movieshub.utils.Constants.API_KEY
import com.practice.movieshub.utils.Constants.POSTER_URL
import com.practice.movieshub.utils.Constants.convertToHour
import com.practice.movieshub.utils.ToastUtils.shortToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = arguments?.getInt("movie_id")

        val call: Call<MovieDetailModel> = RetrofitInstance.api.getMovieDetails(movieId!!, API_KEY)
        call.enqueue(object : Callback<MovieDetailModel> {
            override fun onResponse(
                call: Call<MovieDetailModel>,
                response: Response<MovieDetailModel>
            ) {
                val data = response.body()
                if (data != null && response.isSuccessful) {
                    binding.movieDetailsProgress.visibility = View.GONE
                    binding.mainDetailLayout.visibility = View.VISIBLE
                    binding.movieName.text = data.title
                    binding.releaseDateAndTime.text = data.release_date
                    "${data.vote_average}/10".also { binding.rating.text = it }
                    if (data.genres.isNotEmpty()) {
                        binding.genre.text = data.genres[0].name
                    }
                    Glide.with(requireContext()).load("$POSTER_URL${data.backdrop_path}")
                        .into(binding.posterBackgroundImage)
                    Glide.with(requireContext()).load("$POSTER_URL${data.poster_path}")
                        .into(binding.posterImage)
                    binding.movieDetails.text = data.overview
                    binding.duration.text = convertToHour(data.runtime)

                }
            }

            override fun onFailure(call: Call<MovieDetailModel>, t: Throwable) {
                shortToast(requireContext(), "Error Fetching Movie Data")
            }

        })
        getSimilar(movieId)
        getCast(movieId)


    }

    private fun getCast(myId: Int) {
        val callCast: Call<MovieCastModel> = RetrofitInstance.api.getMovieCast(myId, API_KEY)
        callCast.enqueue(object : Callback<MovieCastModel> {
            override fun onResponse(
                call: Call<MovieCastModel>,
                response: Response<MovieCastModel>
            ) {
                val data = response.body()
                if (data != null && response.isSuccessful) {
                    Log.d("Cast Data", data.toString())
                    val myAdapter = CastMovieAdapter(data.cast as ArrayList<Cast>)
                    binding.castRecyclerView.adapter = myAdapter
                    myAdapter.setOnClickListener(object : CastMovieAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            shortToast(requireContext(), data.cast[position].original_name)
                        }

                    })

                }
            }

            override fun onFailure(call: Call<MovieCastModel>, t: Throwable) {
                shortToast(requireContext(), "Error Fetching Cast Data")
            }
        })
    }

    private fun getSimilar(myId: Int) {
        val call: Call<TypeOfMovieModel> = RetrofitInstance.api.getSimilarMovies(myId, API_KEY)
        call.enqueue(object : Callback<TypeOfMovieModel> {
            override fun onResponse(
                call: Call<TypeOfMovieModel>,
                response: Response<TypeOfMovieModel>
            ) {
                val data = response.body()
                if (data != null && response.isSuccessful) {
                    binding.similarMovieProgress.visibility = View.GONE
                    binding.similarMovie.visibility = View.VISIBLE
                    val myAdapter = LatestMovieAdapter(data)
                    binding.similarMovie.adapter = myAdapter
                    myAdapter.setClickListener(object : LatestMovieAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val action =
                                DetailsFragmentDirections.actionDetailsFragmentSelf(data.results[position].id)
                            findNavController().navigate(action)
                        }

                    })
                }
            }

            override fun onFailure(call: Call<TypeOfMovieModel>, t: Throwable) {
                shortToast(requireContext(), "ERROR IN GETTING SIMILAR MOVIES")
            }

        })
    }
}