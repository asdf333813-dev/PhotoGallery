package com.example.photogallery.network

import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("services/rest/")
    suspend fun fetchInterestingPhotos(
        @Query("method") method: String = "flickr.interestingness.getList",
        @Query("api_key") apiKey: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("extras") extras: String = "url_s"
    ): FlickrResponse
}