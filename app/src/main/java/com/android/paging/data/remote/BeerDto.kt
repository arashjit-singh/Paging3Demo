package com.android.paging.data.remote

data class BeerDto(
    val abv: Double,
    val description: String,
    val first_brewed: String,
    val ibu: Double,
    val id: Int,
    val image_url: String,
    val name: String,
    val ph: Double,
    val srm: Double,
    val tagline: String,
    val target_fg: Double,
    val target_og: Double
)