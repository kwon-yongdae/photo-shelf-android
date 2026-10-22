package com.yongdae.photoshelf.core.domain.model

data class PhotoUser(
    val id: String,
    val username: String,
    val name: String,
    val profileImage: ProfileImage,
)