package com.example.photogallery.network

import retrofit2.http.GET
import retrofit2.http.Query

interface PicsumApi {

    @GET("products")
    suspend fun fetchPhotos(
        @Query("limit") limit: Int = 20
    ): PicsumResponse
}