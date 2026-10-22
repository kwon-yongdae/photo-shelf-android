package com.yongdae.photoshelf.core.data.datasorce.remote

import com.yongdae.photoshelf.core.data.datasorce.remote.model.toModel
import com.yongdae.photoshelf.core.data.datasorce.remote.service.PhotosService
import com.yongdae.photoshelf.core.domain.model.Photo
import javax.inject.Inject

class RemotePhotosDataSource @Inject constructor(
    private val photosService: PhotosService
) {
    suspend fun getPhotos(page: Int?, perPage: Int?): List<Photo> {
        return photosService.getPhotos(page, perPage).map { it.toModel() }
    }

    suspend fun getPhoto(id: String): Photo {
        return photosService.getPhoto(id).toModel()
    }

}

