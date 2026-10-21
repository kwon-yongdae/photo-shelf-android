package com.yongdae.photoshelf.core.domain.repository

import com.yongdae.photoshelf.core.domain.model.Photo

interface PhotosRepository {
    suspend fun getPhotos(page: Int?, perPage: Int?): List<Photo>

    suspend fun getPhoto(id: String): Photo

    suspend fun getFavoritePhotos(page: Int?, perPage: Int?): List<Photo>

    suspend fun toggleFavorite(photo: Photo)
}
