package com.yongdae.photoshelf.core.data.datasorce.local

import com.yongdae.photoshelf.core.data.datasorce.local.dao.FavoritePhotosDao
import com.yongdae.photoshelf.core.data.datasorce.local.entity.toEntity
import com.yongdae.photoshelf.core.data.datasorce.local.entity.toModel
import com.yongdae.photoshelf.core.domain.model.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalPhotosDataSource @Inject constructor(
    private val favoritePhotosDao: FavoritePhotosDao
) {
    fun observeIsFavorite(): Flow<List<Photo>> {
        return favoritePhotosDao.observeAll().map { it.map { it.toModel() } }
    }

    fun observeFavoriteIds(): Flow<Set<String>> {
        return favoritePhotosDao.observeFavoriteIds().map { ids -> ids.toSet() }
    }

    suspend fun toggleFavorite(photo: Photo) {
        val existing = favoritePhotosDao.getById(photo.id)

        if (existing == null) {
            favoritePhotosDao.insert(photo.toEntity())
        } else {
            favoritePhotosDao.deleteById(photo.id)
        }
    }

    suspend fun getPhoto(id: String): Photo {
        return favoritePhotosDao.getById(id)?.toModel()
            ?: throw NoSuchElementException("ID가 $id 인 좋아요 사진을 찾을 수 없습니다.")
    }

    fun observeIsFavorite(id: String): Flow<Boolean> {
        return favoritePhotosDao.observeIsFavorite(id)
    }
}
