package com.example.photogallery

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photogallery.network.PicsumPhoto
import com.example.photogallery.ui.theme.PhotoGalleryTheme
import com.example.photogallery.viewmodel.PhotoGalleryViewModel
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PhotoGalleryTheme {
                PhotoGalleryScreen()
            }
        }
    }
}

@Composable
fun PhotoGalleryScreen(
    modifier: Modifier = Modifier,
    viewModel: PhotoGalleryViewModel = viewModel()
) {
    val photos by viewModel.photos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator()
            }

            photos.isEmpty() -> {
                Text(text = "No photos loaded")
            }

            else -> {
                PhotoGrid(photos = photos)
            }
        }
    }
}

@Composable
fun PhotoGrid(photos: List<PicsumPhoto>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(2.dp)
    ) {
        items(photos) { photo ->
            Box(
                modifier = Modifier
                    .padding(1.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = photo.id.toString(),
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoGalleryScreenPreview() {
    PhotoGalleryTheme {
        PhotoGalleryScreen()
    }
}