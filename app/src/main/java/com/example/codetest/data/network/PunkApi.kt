package com.example.codetest.data.network

import com.example.codetest.data.entities.BeerVO
import com.example.codetest.utils.GET_ALL_BEER
import retrofit2.http.GET
import retrofit2.http.Query

interface PunkApi {
    @GET(GET_ALL_BEER)
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : List<BeerVO>?
}