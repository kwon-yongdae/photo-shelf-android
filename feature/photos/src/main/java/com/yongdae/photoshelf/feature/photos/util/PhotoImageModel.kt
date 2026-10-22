package com.yongdae.photoshelf.feature.photos.util

import com.yongdae.photoshelf.core.domain.model.Photo
import java.io.File

internal fun Photo.imageModel(remoteUrl: String): Any {
    val file = localPath?.let(::File)
    return if (file != null && file.exists()) file else remoteUrl
}

internal val Photo.ratio: Float
    get() = if (width > 0 && height > 0) width.toFloat() / height else 0.9f
