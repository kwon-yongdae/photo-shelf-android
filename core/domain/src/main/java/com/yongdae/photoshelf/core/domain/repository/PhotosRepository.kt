package com.yongdae.photoshelf.core.domain.repository

import com.yongdae.photoshelf.core.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {
    suspend fun getPhotos(page: Int?, perPage: Int?): List<Photo>

    suspend fun getPhoto(id: String): Photo

    fun observeFavoritePhotos(): Flow<List<Photo>>

    suspend fun toggleFavorite(photo: Photo)

    fun observeIsFavorite(id: String): Flow<Boolean>
}
