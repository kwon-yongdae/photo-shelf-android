package com.yongdae.photoshelf.core.data.datasorce.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yongdae.photoshelf.core.domain.model.Photo
import com.yongdae.photoshelf.core.domain.model.PhotoUrls
import com.yongdae.photoshelf.core.domain.model.PhotoUser
import com.yongdae.photoshelf.core.domain.model.ProfileImage

@Entity(tableName = "favorite_photos")
data class PhotoEntity(
    @PrimaryKey
    val id: String,

    val width: Int,
    val height: Int,
    val description: String,

    val rawUrl: String,
    val fullUrl: String,
    val regularUrl: String,
    val smallUrl: String,
    val thumbUrl: String,

    val userId: String,
    val username: String,
    val name: String,

    val profileImageSmall: String,
    val profileImageMedium: String,
    val profileImageLarge: String,
)

fun PhotoEntity.toModel(): Photo =
    Photo(
        id = id,
        width = width,
        height = height,
        description = description,
        urls = PhotoUrls(
            raw = rawUrl,
            full = fullUrl,
            regular = regularUrl,
            small = smallUrl,
            thumb = thumbUrl,
        ),
        user = PhotoUser(
            id = userId,
            username = username,
            name = name,
            profileImage = ProfileImage(
                small = profileImageSmall,
                medium = profileImageMedium,
                large = profileImageLarge,
            ),
        ),
    )
