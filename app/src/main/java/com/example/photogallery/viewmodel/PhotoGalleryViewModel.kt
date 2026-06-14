package com.example.photogallery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photogallery.network.PicsumPhoto
import com.example.photogallery.network.PicsumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhotoGalleryViewModel : ViewModel() {

    private val repository = PicsumRepository()

    private val _photos = MutableStateFlow<List<PicsumPhoto>>(emptyList())
    val photos: StateFlow<List<PicsumPhoto>> = _photos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadPhotos()
    }

    fun loadPhotos() {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                _photos.value = repository.fetchPhotos()
            }
            catch (e: Exception) {
                e.printStackTrace()
                _photos.value = emptyList()
            }

            _isLoading.value = false
        }
    }
}