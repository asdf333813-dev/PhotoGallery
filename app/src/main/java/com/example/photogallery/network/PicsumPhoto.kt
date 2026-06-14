package com.example.photogallery.network

import com.squareup.moshi.Json

data class PicsumPhoto(
    val id: Int,
    val title: String,
    @Json(name = "url")
    val download_url: String
)