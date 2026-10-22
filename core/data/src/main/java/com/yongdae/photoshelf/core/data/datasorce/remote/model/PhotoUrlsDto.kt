package com.yongdae.photoshelf.core.data.datasorce.remote.model

import com.yongdae.photoshelf.core.domain.model.PhotoUrls
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoUrlsDto(
    @SerialName("raw") val raw: String,
    @SerialName("full") val full: String,
    @SerialName("regular") val regular: String,
    @SerialName("small") val small: String,
    @SerialName("thumb") val thumb: String,
)

fun PhotoUrlsDto.toModel(): PhotoUrls =
    PhotoUrls(
        raw = raw,
        full = full,
        regular = regular,
        small = small,
        thumb = thumb,
    )
