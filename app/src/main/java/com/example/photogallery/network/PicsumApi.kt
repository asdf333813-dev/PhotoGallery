package com.example.photogallery.network

import retrofit2.http.GET

interface PicsumApi {

    @GET("photos")
    suspend fun fetchPhotos(
        @retrofit2.http.Query("_limit") limit: Int = 30
    ): List<PicsumPhoto>
}