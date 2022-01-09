package com.practice.movieshub.models.movieCast

data class MovieCastModel(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)