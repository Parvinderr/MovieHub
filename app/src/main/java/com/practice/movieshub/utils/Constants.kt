package com.practice.movieshub.utils

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "a7bcf108bb5f8d638ffb151cc30e1127"
    const val POSTER_URL = "https://image.tmdb.org/t/p/w185"


    fun convertToHour(min: Int): String {
        val h = min / 60
        val m = min % 60
        return "${h}h ${m}m"
    }

}