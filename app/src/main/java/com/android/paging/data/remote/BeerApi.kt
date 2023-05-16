package com.android.paging.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface BeerApi {

    @GET("beers/")
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<BeerDto>

}