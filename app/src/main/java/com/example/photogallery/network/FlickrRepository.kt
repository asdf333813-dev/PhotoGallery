package com.example.photogallery.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class FlickrRepository {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.flickr.com/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val flickrApi = retrofit.create(FlickrApi::class.java)

    suspend fun fetchPhotos(apiKey: String): List<GalleryItem> {
        return flickrApi
            .fetchInterestingPhotos(apiKey = apiKey)
            .photos
            .photo
            .filter { it.url != null }
    }
}