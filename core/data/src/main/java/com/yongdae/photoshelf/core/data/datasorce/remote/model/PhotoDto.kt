package com.yongdae.photoshelf.core.data.datasorce.remote.model

import com.yongdae.photoshelf.core.domain.model.Photo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDto(
    @SerialName("id") val id: String,
    @SerialName("created_at") val createdAt: String?,
    @SerialName("updated_at") val updatedAt: String?,
    @SerialName("width") val width: Int?,
    @SerialName("height") val height: Int?,
    @SerialName("color") val color: String?,
    @SerialName("blur_hash") val blurHash: String?,
    @SerialName("downloads") val downloads: Int? = null,
    @SerialName("public_domain") val publicDomain: Boolean? = null,
    @SerialName("description") val description: String?,
    @SerialName("alt_description") val altDescription: String?,
    @SerialName("user") val user: PhotoUserDto?,
    @SerialName("exif") val exif: PhotoExifDto? = null,
    @SerialName("tags") val tags: List<PhotoTagDto> = emptyList(),
    @SerialName("location") val location: PhotoLocationDto? = null,
    @SerialName("current_user_collections") val currentUserCollections: List<PhotoCollectionDto>,
    @SerialName("urls") val urls: PhotoUrlsDto?,
    @SerialName("links") val links: PhotoLinksDto?,
)

fun PhotoDto.toModel(): Photo =
    Photo(
        id = id,
        width = width,
        height = height,
        description = description,
        urls = urls?.toModel(),
        user = user?.toModel(),
    )
