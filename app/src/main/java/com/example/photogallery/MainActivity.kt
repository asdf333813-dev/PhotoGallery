package com.example.photogallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.photogallery.network.PicsumPhoto
import com.example.photogallery.ui.theme.PhotoGalleryTheme
import com.example.photogallery.viewmodel.PhotoGalleryViewModel
import androidx.compose.ui.platform.LocalContext
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoGalleryScreen(
    modifier: Modifier = Modifier,
    viewModel: PhotoGalleryViewModel = viewModel()
) {
    val photos by viewModel.photos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var searchText by remember { mutableStateOf("") }
    var menuExpanded by remember { mutableStateOf(false) }
    var showFavorites by remember { mutableStateOf(false) }
    var favoritePhotos by remember { mutableStateOf<List<PicsumPhoto>>(emptyList()) }

    val filteredPhotos = photos.filter {
        it.title.contains(searchText, ignoreCase = true) ||
                it.id.toString().contains(searchText)
    }

    val visiblePhotos = if (showFavorites) {
        favoritePhotos
    } else {
        filteredPhotos
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (showFavorites) {
                            "Favorite Photos"
                        } else {
                            "PhotoGallery"
                        }
                    )
                },
                actions = {
                    Button(
                        onClick = {
                            menuExpanded = true
                        }
                    ) {
                        Text("Menu")
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = {
                            menuExpanded = false
                        }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Show favorites") },
                            onClick = {
                                showFavorites = true
                                menuExpanded = false
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Show all photos") },
                            onClick = {
                                showFavorites = false
                                menuExpanded = false
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Clear favorites") },
                            onClick = {
                                favoritePhotos = emptyList()
                                menuExpanded = false
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.Gray)
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    showFavorites = false
                },
                label = {
                    Text("Search photos")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp)
                    )
                }

                visiblePhotos.isEmpty() -> {
                    Text(
                        text = "No photos loaded",
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> {
                    PhotoGrid(
                        photos = visiblePhotos,
                        onPhotoClick = { photo ->
                            if (!favoritePhotos.contains(photo)) {
                                favoritePhotos = favoritePhotos + photo
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PhotoGrid(
    photos: List<PicsumPhoto>,
    onPhotoClick: (PicsumPhoto) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(2.dp)
    ) {
        items(photos) { photo ->
            AsyncImage(
                model = photo.thumbnail,
                contentDescription = photo.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(1.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.DarkGray)
                    .clickable {
                        onPhotoClick(photo)
                    }
            )
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