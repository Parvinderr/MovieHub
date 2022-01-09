package com.practice.movieshub.models.typeOfMovie

data class TypeOfMovieModel(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)