package com.android.paging.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface BeerApi {

    @GET("/beers")
    suspend fun getBeers(
        @Path("page") page: Int,
        @Path("per_page") perPage: Int
    ): List<BeerDto>

}