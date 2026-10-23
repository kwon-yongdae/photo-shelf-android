package com.yongdae.photoshelf.feature.photos.detail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yongdae.photoshelf.core.domain.model.Photo
import com.yongdae.photoshelf.feature.photos.component.FavoriteButton
import com.yongdae.photoshelf.feature.photos.util.imageModel
import com.yongdae.photoshelf.feature.photos.util.ratio

@Composable
fun PhotoDetailScreen(
    id: String,
    onBack: () -> Unit,
    viewModel: PhotoDetailViewModel = hiltViewModel(),
) {
    val photo by viewModel.photo.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            PhotoDetailTopAppBar(
                onBack = onBack,
                isFavorite = isFavorite,
                onFavoriteClick = { viewModel.onFavoriteClick() }
            )
        },
    ) { innerPadding ->
        val currentPhoto = photo

        if (currentPhoto == null) {
            CircularProgressIndicator()
        } else {
            PhotoDetailContent(
                photo = currentPhoto,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Composable
private fun PhotoDetailContent(
    photo: Photo,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photo.imageModel(photo.urls.regular))
                .crossfade(true)
                .build(),
            contentDescription = photo.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(photo.ratio)
                .clip(RoundedCornerShape(14.dp)),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photo.user.profileImage.large)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .border(2.dp, Color.White, CircleShape)
                    .padding(2.dp)
                    .clip(CircleShape),
            )

            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = photo.user.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = "@${photo.user.username}",
                    color = Color(0xFF99999F),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        Text(
            text = photo.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(
                start = 24.dp,
                end = 24.dp,
                top = 36.dp,
                bottom = 24.dp,
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PhotoDetailTopAppBar(
    onBack: () -> Unit,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
) {
    TopAppBar(
        title = { Text("Photo Detail") },
        navigationIcon = {
            IconButton(onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            FavoriteButton(
                onClick = onFavoriteClick,
                isFavorite = isFavorite,
            )
        }
    )
}
