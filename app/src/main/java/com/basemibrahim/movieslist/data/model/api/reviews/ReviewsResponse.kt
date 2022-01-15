package com.basemibrahim.movieslist.data.model.api.reviews

data class ReviewsResponse(
    val id: Int,
    val page: Int,
    val results: List<ResultX>,
    val total_pages: Int,
    val total_results: Int
)