package com.practice.movieshub.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.practice.movieshub.adapters.ViewAllAdapter
import com.practice.movieshub.databinding.FragmentViewAllBinding
import com.practice.movieshub.models.typeOfMovie.TypeOfMovieModel
import com.practice.movieshub.network.RetrofitInstance
import com.practice.movieshub.utils.Constants.API_KEY
import com.practice.movieshub.utils.ToastUtils.longToast
import com.practice.movieshub.utils.ToastUtils.shortToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewAllFragment : Fragment() {
    private var _binding: FragmentViewAllBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val i = this.arguments?.getInt("type")
        val q = this.arguments?.getString("query")
        when (i) {
            0 -> upcomingMovie()
            1 -> trendingMovie()
            2 -> nowPlayingMovie()
            3 -> popularMovie()
            4 -> topRatedMovie()
            5 -> discoverMovie(q!!)
        }
        longToast(requireContext(), i.toString())
    }

    private fun discoverMovie(query: String) {
        val callDiscoverMovie = RetrofitInstance.api.searchMovie(query, API_KEY, 1)
        callDiscoverMovie.enqueue(object : Callback<TypeOfMovieModel> {
            override fun onResponse(
                call: Call<TypeOfMovieModel>,
                response: Response<TypeOfMovieModel>
            ) {
                val data = response.body()
                Log.d("Searched Data", data.toString())
                if (data != null && response.isSuccessful && data.results.isNotEmpty()) {
                    binding.typeOfMovieProgressbar.visibility = View.GONE
                    binding.viewAllRecyclerView.visibility = View.VISIBLE
                    val myAdapter = ViewAllAdapter(data)
                    binding.viewAllRecyclerView.adapter = myAdapter
                    myAdapter.setOnClickListener(object : ViewAllAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            longToast(requireContext(), data.results[position].id.toString())
                            val action =
                                ViewAllFragmentDirections.actionViewAllFragment2ToDetailsFragment2(
                                    data.results[position].id
                                )
                            findNavController().navigate(action)
                        }

                    })

                } else {
                    binding.typeOfMovieProgressbar.visibility = View.GONE
                    binding.notFound.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<TypeOfMovieModel>, t: Throwable) {
                shortToast(requireContext(), "Search Movie Failed")
            }

        })
    }

    private fun upcomingMovie() {
        val callUpcomingMovie = RetrofitInstance.api.getUpcomingMovies(API_KEY, 1)
        callUpcomingMovie.enqueue(object : Callback<TypeOfMovieModel> {
            override fun onResponse(
                call: Call<TypeOfMovieModel>,
                response: Response<TypeOfMovieModel>
            ) {
                val data = response.body()
                if (data != null && response.isSuccessful) {
                    binding.typeOfMovieProgressbar.visibility = View.GONE
                    binding.viewAllRecyclerView.visibility = View.VISIBLE
                    val myAdapter = ViewAllAdapter(data)
                    binding.viewAllRecyclerView.adapter = myAdapter
                    myAdapter.setOnClickListener(object : ViewAllAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            longToast(requireContext(), data.results[position].id.toString())
                            val action =
                                ViewAllFragmentDirections.actionViewAllFragment2ToDetailsFragment2(
                                    data.results[position].id
                                )
                            findNavController().navigate(action)
                        }

                    })

                }

            }

            override fun onFailure(call: Call<TypeOfMovieModel>, t: Throwable) {
                longToast(requireContext(), t.message.toString())
                Log.d("ERROR", t.message.toString())
            }

        })

    }

    private fun trendingMovie() {

        val callTrendingMovie = RetrofitInstance.api.getTrendingMovies(API_KEY, 1)
        callTrendingMovie.enqueue(object : Callback<TypeOfMovieModel> {
            override fun onResponse(
                call: Call<TypeOfMovieModel>,
                response: Response<TypeOfMovieModel>
            ) {

                val data = response.body()
                if (data != null && response.isSuccessful) {
                    binding.typeOfMovieProgressbar.visibility = View.GONE
                    binding.viewAllRecyclerView.visibility = View.VISIBLE
                    val myAdapter = ViewAllAdapter(data)
                    binding.viewAllRecyclerView.adapter = myAdapter



                    myAdapter.setOnClickListener(object : ViewAllAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            longToast(requireContext(), data.results[position].id.toString())
                            val action =
                                ViewAllFragmentDirections.actionViewAllFragment2ToDetailsFragment2(
                                    data.results[position].id
                                )
                            findNavController().navigate(action)
                        }

                    })

                }
            }

            override fun onFailure(call: Call<TypeOfMovieModel>, t: Throwable) {
                longToast(requireContext(), t.message.toString())
                Log.d("ERROR", t.message.toString())
            }

        })


    }

    private fun nowPlayingMovie() {
        val callNowPlayingMovie = RetrofitInstance.api.getNowPlayingMovies(API_KEY, 1)
        callNowPlayingMovie.enqueue(object : Callback<TypeOfMovieModel> {
            override fun onResponse(
                call: Call<TypeOfMovieModel>,
                response: Response<TypeOfMovieModel>
            ) {
                val data = response.body()
                if (data != null && response.isSuccessful) {
                    binding.typeOfMovieProgressbar.visibility = View.GONE
                    binding.viewAllRecyclerView.visibility = View.VISIBLE
                    val myAdapter = ViewAllAdapter(data)
                    binding.viewAllRecyclerView.adapter = myAdapter



                    myAdapter.setOnClickListener(object : ViewAllAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            longToast(requireContext(), data.results[position].id.toString())
                            val action =
                                ViewAllFragmentDirections.actionViewAllFragment2ToDetailsFragment2(
                                    data.results[position].id
                                )
                            findNavController().navigate(action)
                        }

                    })

                }
            }

            override fun onFailure(call: Call<TypeOfMovieModel>, t: Throwable) {
                longToast(requireContext(), t.message.toString())
                Log.d("ERROR", t.message.toString())
            }

        })

    }

    private fun popularMovie() {
        val callPopularMovie = RetrofitInstance.api.getPopularMovies(API_KEY, 1)
        callPopularMovie.enqueue(object : Callback<TypeOfMovieModel> {
            override fun onResponse(
                call: Call<TypeOfMovieModel>,
                response: Response<TypeOfMovieModel>
            ) {
                val data = response.body()
                if (data != null && response.isSuccessful) {
                    binding.typeOfMovieProgressbar.visibility = View.GONE
                    binding.viewAllRecyclerView.visibility = View.VISIBLE
                    val myAdapter = ViewAllAdapter(data)
                    binding.viewAllRecyclerView.adapter = myAdapter



                    myAdapter.setOnClickListener(object : ViewAllAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            longToast(requireContext(), data.results[position].id.toString())
                            val action =
                                ViewAllFragmentDirections.actionViewAllFragment2ToDetailsFragment2(
                                    data.results[position].id
                                )
                            findNavController().navigate(action)
                        }

                    })

                }
            }

            override fun onFailure(call: Call<TypeOfMovieModel>, t: Throwable) {
                longToast(requireContext(), t.message.toString())
                Log.d("ERROR", t.message.toString())
            }

        })

    }

    private fun topRatedMovie() {
        val callTopRatedMovie = RetrofitInstance.api.getTopRatedMovies(API_KEY, 1)
        callTopRatedMovie.enqueue(object : Callback<TypeOfMovieModel> {
            override fun onResponse(
                call: Call<TypeOfMovieModel>,
                response: Response<TypeOfMovieModel>
            ) {
                val data = response.body()
                if (data != null && response.isSuccessful) {
                    binding.typeOfMovieProgressbar.visibility = View.GONE
                    binding.viewAllRecyclerView.visibility = View.VISIBLE
                    val myAdapter = ViewAllAdapter(data)
                    binding.viewAllRecyclerView.adapter = myAdapter



                    myAdapter.setOnClickListener(object : ViewAllAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            longToast(requireContext(), data.results[position].id.toString())
                            val action =
                                ViewAllFragmentDirections.actionViewAllFragment2ToDetailsFragment2(
                                    data.results[position].id
                                )
                            findNavController().navigate(action)
                        }

                    })

                }


            }

            override fun onFailure(call: Call<TypeOfMovieModel>, t: Throwable) {
                longToast(requireContext(), t.message.toString())
                Log.d("ERROR", t.message.toString())
            }

        })


    }
}