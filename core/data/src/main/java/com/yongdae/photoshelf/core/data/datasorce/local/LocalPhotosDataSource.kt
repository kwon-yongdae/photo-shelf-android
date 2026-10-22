package com.yongdae.photoshelf.core.data.datasorce.local

import com.yongdae.photoshelf.core.data.datasorce.PhotosDataSource
import com.yongdae.photoshelf.core.domain.model.Photo
import javax.inject.Inject

class LocalPhotosDataSource @Inject constructor(
) : PhotosDataSource {
    override suspend fun getPhotos(page: Int?, perPage: Int?): List<Photo> {
        TODO("Not yet implemented")
    }

    override suspend fun getPhoto(id: String): Photo {
        TODO("Not yet implemented")
    }
}
