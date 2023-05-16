package com.android.paging.domain

data class Beer(
    val beerId: Int,
    val beerDescription: String,
    val beerFirstBrewedDate: String,
    val beerImageUrl: String?,
    val beerName: String,
    val beerTagline: String
)
