package com.yongdae.photoshelf.feature.photos.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yongdae.photoshelf.feature.photos.component.PhotoItem
import com.yongdae.photoshelf.feature.photos.util.responsiveStaggeredGridCells

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritePhotosScreen(
    viewModel: FavoritePhotosViewModel = hiltViewModel(),
    onPhotoClick: (String) -> Unit,
    onBack: () -> Unit
) {
    val listState = rememberLazyStaggeredGridState()
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle(
        initialValue = emptySet()
    )

    LaunchedEffect(listState) {
        viewModel.loadPage()
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Favorite Photos") },
            navigationIcon = {
                IconButton(onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
        )
    }) { innerPadding ->
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
                    PhotoItem(
                        photo,
                        isFavorite = photo.id in favoriteIds,
                        onClick = { onPhotoClick(photo.id) },
                        onFavoriteClick = { viewModel.onFavoriteClick(photo) },
                        modifier = Modifier.animateItem()
                    )
                }
            },
        )
    }
}
