package com.yongdae.photoshelf.core.data.datasorce.local

import com.yongdae.photoshelf.core.data.datasorce.PhotosDataSource
import com.yongdae.photoshelf.core.data.datasorce.local.dao.FavoritePhotosDao
import com.yongdae.photoshelf.core.data.datasorce.local.entity.toModel
import com.yongdae.photoshelf.core.domain.model.Photo
import javax.inject.Inject

class LocalPhotosDataSource @Inject constructor(
    private val favoritePhotosDao: FavoritePhotosDao
) : PhotosDataSource {
    override suspend fun getPhotos(page: Int?, perPage: Int?): List<Photo> {
        return emptyList()
    }

    override suspend fun getPhoto(id: String): Photo {
        return favoritePhotosDao.getById(id).toModel()
//            ?: throw NoSuchElementException("ID가 $id 인 좋아요 사진을 찾을 수 없습니다.")
    }
}
