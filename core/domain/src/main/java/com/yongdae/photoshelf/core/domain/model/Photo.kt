package com.yongdae.photoshelf.core.domain.model

data class Photo(
    val id: String,
    val width: Int,
    val height: Int,
    val description: String,
    val urls: PhotoUrls,
    val user: PhotoUser,
)
