package com.yongdae.photoshelf.core.data.datasorce.remote.model

import com.yongdae.photoshelf.core.domain.model.PhotoUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoUserDto(
    @SerialName("id") val id: String,
    @SerialName("updated_at") val updatedAt: String?,
    @SerialName("username") val username: String,
    @SerialName("name") val name: String,
    @SerialName("portfolio_url") val portfolioUrl: String?,
    @SerialName("bio") val bio: String?,
    @SerialName("location") val location: String?,
    @SerialName("total_collections") val totalCollections: Int?,
    @SerialName("instagram_username") val instagramUsername: String?,
    @SerialName("twitter_username") val twitterUsername: String?,
    @SerialName("profile_image") val profileImage: ProfileImageDto,
    @SerialName("links") val links: UserLinksDto?,
)

fun PhotoUserDto.toModel(): PhotoUser =
    PhotoUser(
        id = id,
        username = username,
        name = name,
        profileImage = profileImage.toModel(),
    )
