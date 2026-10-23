package com.yongdae.photoshelf.feature.photos.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
internal fun FavoriteButton(
    onClick: () -> Unit,
    isFavorite: Boolean,
) {
    IconButton(
        onClick = onClick,
    ) {
        if (isFavorite) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "즐겨찾기 해제",
                tint = Color(0xFFE56875),
            )
        } else {
            Icon(
                Icons.Outlined.FavoriteBorder,
                contentDescription = "즐겨찾기 추가",
                tint = Color(0xFFE56875),
            )
        }
    }
}