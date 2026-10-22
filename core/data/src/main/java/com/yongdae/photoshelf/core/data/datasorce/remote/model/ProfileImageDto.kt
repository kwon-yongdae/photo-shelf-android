package com.yongdae.photoshelf.core.data.datasorce.remote.model

import com.yongdae.photoshelf.core.domain.model.ProfileImage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileImageDto(
    @SerialName("small") val small: String,
    @SerialName("medium") val medium: String,
    @SerialName("large") val large: String,
)

fun ProfileImageDto.toModel(): ProfileImage =
    ProfileImage(
        small = small,
        medium = medium,
        large = large,
    )
