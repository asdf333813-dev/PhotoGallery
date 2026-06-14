package com.example.photogallery.network

import com.squareup.moshi.Json

data class FlickrResponse(
    val photos: PhotoPage
)

data class PhotoPage(
    val photo: List<GalleryItem>
)

data class GalleryItem(
    val id: String,
    val title: String,
    @Json(name = "url_s")
    val url: String?
)