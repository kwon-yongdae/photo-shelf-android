package com.yongdae.photoshelf.feature.photos.util

import com.yongdae.photoshelf.core.domain.model.Photo

internal val Photo.ratio: Float
    get() = if (width > 0 && height > 0) width.toFloat() / height else 0.9f
