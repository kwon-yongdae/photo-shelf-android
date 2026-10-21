package com.yongdae.photoshelf.core.data.datasorce.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoExifDto(
    @SerialName("make") val make: String? = null,
    @SerialName("model") val model: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("exposure_time") val exposureTime: String? = null,
    @SerialName("aperture") val aperture: String? = null,
    @SerialName("focal_length") val focalLength: String? = null,
    @SerialName("iso") val iso: Int? = null,
)
