package com.raaziat.pagingexample.model.places

data class NearbySearch(
    val html_attributions: List<Any>,
    val next_page_token: String,
    val results: List<Result>,
    val status: String
)