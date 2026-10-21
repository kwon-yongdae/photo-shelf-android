package com.yongdae.photoshelf.core.data.datasorce.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoLocationDto(
    @SerialName("city") val city: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("position") val position: PhotoPositionDto? = null,
)