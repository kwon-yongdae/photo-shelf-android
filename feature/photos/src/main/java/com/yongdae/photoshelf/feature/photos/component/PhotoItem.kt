package com.yongdae.photoshelf.feature.photos.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yongdae.photoshelf.core.domain.model.Photo
import com.yongdae.photoshelf.feature.photos.util.imageModel
import com.yongdae.photoshelf.feature.photos.util.ratio

@Composable
internal fun PhotoItem(
    photo: Photo,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF29292C))
            .clickable(onClick = onClick),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photo.imageModel(photo.urls.thumb))
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(photo.ratio),
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

            FavoriteButton(
                onClick = onFavoriteClick,
                enabled = true,
                isFavorite = isFavorite
            )
        }
    }
}
