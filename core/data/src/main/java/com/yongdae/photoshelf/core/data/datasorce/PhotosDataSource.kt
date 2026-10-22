package com.yongdae.photoshelf.core.data.datasorce

import com.yongdae.photoshelf.core.domain.model.Photo

interface PhotosDataSource {
    suspend fun getPhotos(page: Int?, perPage: Int?): List<Photo>

    suspend fun getPhoto(id: String): Photo

}