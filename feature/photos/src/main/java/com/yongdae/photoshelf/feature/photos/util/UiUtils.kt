package com.yongdae.photoshelf.feature.photos.util

import android.content.res.Configuration
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun isLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

@Composable
fun responsiveStaggeredGridCells(): StaggeredGridCells {
    val columns = if (isLandscape()) LANDSCAPE_COLUMN_COUNT else PORTRAIT_COLUMN_COUNT
    return StaggeredGridCells.Fixed(columns)
}

private const val LANDSCAPE_COLUMN_COUNT = 3
private const val PORTRAIT_COLUMN_COUNT = 2