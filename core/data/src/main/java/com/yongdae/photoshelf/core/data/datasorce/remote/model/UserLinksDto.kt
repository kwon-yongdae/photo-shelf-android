package com.yongdae.photoshelf.core.data.datasorce.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLinksDto(
    @SerialName("self") val self: String?,
    @SerialName("html") val html: String?,
    @SerialName("photos") val photos: String?,
)