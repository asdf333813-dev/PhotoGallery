package com.example.photogallery.network

data class PicsumResponse(
    val products: List<PicsumPhoto>
)

data class PicsumPhoto(
    val id: Int,
    val title: String,
    val thumbnail: String
)