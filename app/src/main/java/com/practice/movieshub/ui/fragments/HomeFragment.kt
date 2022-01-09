package com.practice.movieshub.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import com.practice.movieshub.R
import com.practice.movieshub.adapters.LatestMovieAdapter
import com.practice.movieshub.adapters.TrendingMovieAdapter
import com.practice.movieshub.databinding.FragmentHomeBinding
import com.practice.movieshub.models.typeOfMovie.TypeOfMovieModel
import com.practice.movieshub.network.RetrofitInstance
import com.practice.movieshub.utils.Constants.API_KEY
import com.practice.movieshub.utils.ToastUtils.longToast
import com.practice.movieshub.utils.ToastUtils.shortToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myToolbar.mainToolbar.inflateMenu(R.menu.menu_item)
        val cancelButton: ImageButton = view.findViewById(R.id.cancel_button)
        val searchButton: ImageButton = view.findViewById(R.id.search_button)
        val et: EditText = view.findViewById(R.id.search_text)

        binding.myToolbar.mainToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search -> {
                    shortToast(requireContext(), "Search")
                    binding.myToolbar.mainToolbar.visibility = View.GONE
                    et.isFocusable = true
                    et.isActivated = true

                    binding.myToolbar.searchToolbar.visibility = View.VISIBLE
                    false
                }
                R.id.feedback -> {
                    shortToast(requireContext(), "Feedback")
                    val action = HomeFragmentDirections.actionHomeFragmentToFeedbackFragment()
                    findNavController().navigate(action)
                    false
                }
                R.id.help_support -> {
                    shortToast(requireContext(), "Support")
                    val action = HomeFragmentDirections.actionHomeFragmentToHelpSupportFragment()
                    findNavController().navigate(action)
                    false
                }
                else -> true
            }
        }

        et.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    val query = et.text.toString()
                    if (query.isNotEmpty()) {
                        Log.d("Search Query", query)
                        et.text.clear()
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToViewAllFragment2(
                                5, query
                            )
                        findNavController().navigate(action)
                    }
                    false
                }
                else -> false
            }
        }
        cancelButton.setOnClickListener {

            binding.myToolbar.searchToolbar.visibility = View.GONE
            et.text.clear()
            binding.myToolbar.mainToolbar.visibility = View.VISIBLE
        }

        searchButton.setOnClickListener {

            shortToast(requireContext(), "Search Clicked")

            val query = et.text.toString()
            if (query.isNotEmpty()) {
                Log.d("Search Query", query)
                et.text.clear()
                val action =
                    HomeFragmentDirections.actionHomeFragmentToViewAllFragment2(
                        5, query
                    )
                findNavController().navigate(action)

            } else {
                shortToast(requireContext(), "Enter Movie Name First")
            }


        }


        setHasOptionsMenu(true)
        setData()
        viewAllClickListeners()
    }


    private fun viewAllClickListeners() {
        binding.upcomingMoviesViewAll.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToViewAllFragment2(0, query = null)
            findNavController().navigate(action)
        }
        binding.trendingMoviesViewAll.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToViewAllFragment2(1, null)
            findNavController().navigate(action)
        }
        binding.nowPlayingMoviesViewAll.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToViewAllFragment2(2, null)
            findNavController().navigate(action)
        }
        binding.popularMoviesViewAll.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToViewAllFragment2(3, null)
            findNavController().navigate(action)
        }
        binding.topRatedMoviesViewAll.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToViewAllFragment2(4, null)
            findNavController().navigate(action)
        }
    }

    private fun setData() {

        val callUpcomingMovie = RetrofitInstance.api.getUpcomingMovies(API_KEY, 1)
        callUpcomingMovie.enqueue(object : Callback<TypeOfMovieModel> {
            override fun onResponse(
                call: Call<TypeOfMovieModel>,
                response: Response<TypeOfMovieModel>
            ) {
                val data = response.body()
                if (data != null && response.isSuccessful) {
                    binding.progressUpcoming.visibility = View.GONE
                    binding.upcomingMovies.visibility = View.VISIBLE
                    val myAdapter = LatestMovieAdapter(data)
                    binding.upcomingMovies.adapter = myAdapter

                    myAdapter.setClickListener(object : LatestMovieAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            longToast(requireContext(), data.results[position].toString())
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(data.results[position].id)
                            findNavController().navigate(action)
                        }
                    })
                    binding.upcomingMovies.layoutManager = CarouselLayoutManager(
                        isLoop = false,
                        isItem3D = true,
                        ratio = 1f,
                        flat = true,
                        alpha = false
                    )
                }

            }

            override fun onFailure(call: Call<TypeOfMovieModel>, t: Throwable) {
                longToast(requireContext(), t.message.toString())
                Log.d("ERROR", t.message.toString())
            }

        })


        val callTrendingMovie = RetrofitInstance.api.getTrendingMovies(API_KEY, 1)
        callTrendingMovie.enqueue(object : Callback<TypeOfMovieModel> {
            override fun onResponse(
                call: Call<TypeOfMovieModel>,
                response: Response<TypeOfMovieModel>
            ) {
                val item = response.body()
                if (item != null && response.isSuccessful) {
                    binding.progressTrending.visibility = View.GONE
                    binding.trendingMovie.visibility = View.VISIBLE

                    val myAdapter = TrendingMovieAdapter(item)
                    binding.trendingMovie.adapter = myAdapter
                    myAdapter.setOnClickListener(object :
                        TrendingMovieAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            longToast(requireContext(), item.results[position].id.toString())
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item.results[position].id)
                            findNavController().navigate(action)
                        }
                    })
                    binding.trendingMovie.layoutManager = CarouselLayoutManager(
                        isLoop = true,
                        isItem3D = true,
                        ratio = 30f,
                        flat = false,
                        alpha = false
                    )
                }
            }

            override fun onFailure(call: Call<TypeOfMovieModel>, t: Throwable) {
                longToast(requireContext(), t.message.toString())
                Log.d("ERROR", t.message.toString())
            }

        })

        val callNowPlayingMovie = RetrofitInstance.api.getNowPlayingMovies(API_KEY, 1)
        callNowPlayingMovie.enqueue(object : Callback<TypeOfMovieModel> {
            override fun onResponse(
                call: Call<TypeOfMovieModel>,
                response: Response<TypeOfMovieModel>
            ) {
                val data = response.body()
                if (data != null && response.isSuccessful) {
                    binding.progressNowPlaying.visibility = View.GONE
                    binding.nowPlayingMovies.visibility = View.VISIBLE
                    val myAdapter = LatestMovieAdapter(data)
                    binding.nowPlayingMovies.adapter = myAdapter
                    myAdapter.setClickListener(object : LatestMovieAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            longToast(requireContext(), data.results[position].id.toString())
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(data.results[position].id)
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

        val callPopularMovie = RetrofitInstance.api.getPopularMovies(API_KEY, 1)
        callPopularMovie.enqueue(object : Callback<TypeOfMovieModel> {
            override fun onResponse(
                call: Call<TypeOfMovieModel>,
                response: Response<TypeOfMovieModel>
            ) {
                val data = response.body()
                if (data != null && response.isSuccessful) {
                    binding.progressPopular.visibility = View.GONE
                    binding.popularMovies.visibility = View.VISIBLE
                    val myAdapter = LatestMovieAdapter(data)
                    binding.popularMovies.adapter = myAdapter
                    myAdapter.setClickListener(object : LatestMovieAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            longToast(requireContext(), data.results[position].id.toString())
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(data.results[position].id)
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

        val callTopRatedMovie = RetrofitInstance.api.getTopRatedMovies(API_KEY, 1)
        callTopRatedMovie.enqueue(object : Callback<TypeOfMovieModel> {
            override fun onResponse(
                call: Call<TypeOfMovieModel>,
                response: Response<TypeOfMovieModel>
            ) {
                val data = response.body()
                if (data != null && response.isSuccessful) {
                    binding.progressTopRated.visibility = View.GONE
                    binding.topRatedMovies.visibility = View.VISIBLE
                    val myAdapter = LatestMovieAdapter(data)
                    binding.topRatedMovies.adapter = myAdapter
                    myAdapter.setClickListener(object : LatestMovieAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            longToast(requireContext(), data.results[position].id.toString())
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(data.results[position].id)
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