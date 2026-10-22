package com.yongdae.photoshelf.core.data.repository

import com.yongdae.photoshelf.core.data.datasorce.PhotosDataSource
import com.yongdae.photoshelf.core.data.di.PhotosModule.LocalDataSource
import com.yongdae.photoshelf.core.data.di.PhotosModule.RemoteDataSource
import com.yongdae.photoshelf.core.domain.model.Photo
import com.yongdae.photoshelf.core.domain.repository.PhotosRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPhotosRepository @Inject constructor(
    @param:LocalDataSource private val localPhotosDataSource: PhotosDataSource,
    @param:RemoteDataSource private val remotePhotosDataSource: PhotosDataSource,
) : PhotosRepository {

    override suspend fun getPhotos(
        page: Int?,
        perPage: Int?
    ): List<Photo> {
        return remotePhotosDataSource.getPhotos(page, perPage)
    }

    override suspend fun getPhoto(
        id: String,
    ): Photo {
        return runCatching {
            localPhotosDataSource.getPhoto(id)
        }.onFailure {
            remotePhotosDataSource.getPhoto(id)
        }.getOrThrow()
    }

    override suspend fun getFavoritePhotos(
        page: Int?,
        perPage: Int?
    ): List<Photo> {
        return localPhotosDataSource.getPhotos(page, perPage)
    }

    override suspend fun toggleFavorite(photo: Photo) {

    }
}
