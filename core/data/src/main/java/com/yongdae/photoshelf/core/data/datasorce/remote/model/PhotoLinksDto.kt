package com.yongdae.photoshelf.core.data.datasorce.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoLinksDto(
    @SerialName("self") val self: String?,
    @SerialName("html") val html: String?,
    @SerialName("download") val download: String?,
    @SerialName("download_location") val downloadLocation: String?,
)
