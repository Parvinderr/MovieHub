package com.practice.movieshub.network

import com.practice.movieshub.models.movieCast.MovieCastModel
import com.practice.movieshub.models.movieDetailModel.MovieDetailModel
import com.practice.movieshub.models.typeOfMovie.TypeOfMovieModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroService {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") api: String,
        @Query("page") page: Int
    ): Call<TypeOfMovieModel>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") api: String,
        @Query("page") page: Int
    ): Call<TypeOfMovieModel>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") api: String,
        @Query("page") page: Int
    ): Call<TypeOfMovieModel>

    @GET("trending/movie/week")
    fun getTrendingMovies(
        @Query("api_key") api: String,
        @Query("page") page: Int
    ): Call<TypeOfMovieModel>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") api: String,
        @Query("page") page: Int
    ): Call<TypeOfMovieModel>

    @GET("movie/{id}")
    fun getMovieDetails(
        @Path("id") id: Int, @Query("api_key") api: String,
    ): Call<MovieDetailModel>

    @GET("movie/{id}/credits")
    fun getMovieCast(
        @Path("id") id: Int, @Query("api_key") api: String,
    ): Call<MovieCastModel>

    @GET("search/movie")
    fun searchMovie(
        @Query("query") name: String,
        @Query("api_key") api: String,
        @Query("page") page: Int
    ): Call<TypeOfMovieModel>

    @GET("movie/{id}/similar")
    fun getSimilarMovies(
        @Path("id") id: Int, @Query("api_key") api: String,
    ): Call<TypeOfMovieModel>

}