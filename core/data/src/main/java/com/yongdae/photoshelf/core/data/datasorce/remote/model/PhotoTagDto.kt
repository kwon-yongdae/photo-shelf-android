package com.yongdae.photoshelf.core.data.datasorce.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoTagDto(
    @SerialName("title") val title: String,
)
