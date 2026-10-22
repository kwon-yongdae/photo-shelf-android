package com.yongdae.photoshelf.feature.photos.photos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yongdae.photoshelf.core.domain.model.Photo
import com.yongdae.photoshelf.feature.photos.util.responsiveStaggeredGridCells

private const val PREFETCH_DISTANCE = 5

@Composable
fun LatestPhotosScreen(
    viewModel: LatestPhotosViewModel = hiltViewModel(),
    onPhotoClick: (String) -> Unit,
    goToFavorites: () -> Unit,
) {
    val listState = rememberLazyStaggeredGridState()

    LaunchedEffect(listState, viewModel) {
        snapshotFlow {
            val lastVisibleIndex =
                listState.layoutInfo.visibleItemsInfo.maxOfOrNull { it.index } ?: -1
            lastVisibleIndex to viewModel.state.value.size
        }.collect { (lastVisibleIndex, count) ->
            if (count == 0 || lastVisibleIndex >= count - PREFETCH_DISTANCE) {
                viewModel.loadNextPage()
            }
        }
    }

    Scaffold(
        topBar = {
            LatestPhotosTopAppBar(
                goToFavorites = goToFavorites,
            )
        },
    ) { innerPadding ->
        LazyVerticalStaggeredGrid(
            columns = responsiveStaggeredGridCells(),
            state = listState,
            verticalItemSpacing = 12.dp,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(all = 12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            content = {
                items(
                    items = viewModel.state.value,
                    key = { it.id }
                ) { photo ->
                    Item(
                        photo,
                        onClick = { onPhotoClick(photo.id) },
                        onFavoriteClick = {}
                    )
                }
            },
        )
    }
}

@Composable
private fun Item(
    photo: Photo,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    val ratio = if (photo.width > 0 && photo.height > 0) {
        photo.width.toFloat() / photo.height
    } else {
        0.9f
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF29292C))
            .clickable(onClick = onClick),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photo.urls.thumb)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 6.dp, top = 6.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photo.user.profileImage.medium)
                    .crossfade(true)
                    .build(),
                contentDescription = "${photo.user.name} 프로필",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(44.dp)
                    .border(2.dp, Color.White, CircleShape)
                    .padding(2.dp)
                    .clip(CircleShape),
            )

            Text(
                text = "by",
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp, end = 4.dp),
            )

            Text(
                text = photo.user.name,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )

            IconButton(onFavoriteClick) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "즐겨찾기",
                    tint = Color(0xFFE56875),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LatestPhotosTopAppBar(
    goToFavorites: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                "Unsplash Explorer",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(goToFavorites) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Go to Favorites",
                    tint = Color(0xFFE56875),
                )
            }
        },
    )
}