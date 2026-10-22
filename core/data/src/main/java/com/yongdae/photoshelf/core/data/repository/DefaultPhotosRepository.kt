package com.yongdae.photoshelf.core.data.repository

import com.yongdae.photoshelf.core.data.datasorce.local.LocalPhotosDataSource
import com.yongdae.photoshelf.core.data.datasorce.remote.RemotePhotosDataSource
import com.yongdae.photoshelf.core.domain.model.Photo
import com.yongdae.photoshelf.core.domain.repository.PhotosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPhotosRepository @Inject constructor(
    private val localPhotosDataSource: LocalPhotosDataSource,
    private val remotePhotosDataSource: RemotePhotosDataSource,
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
        return try {
            localPhotosDataSource.getPhoto(id)
        } catch (e: Exception) {
            e.printStackTrace()
            remotePhotosDataSource.getPhoto(id)
        }
    }

    override fun observeFavoritePhotos(): Flow<List<Photo>> {
        return localPhotosDataSource.observeIsFavorite()
    }

    override fun observeFavoriteIds(): Flow<Set<String>> {
        return localPhotosDataSource.observeFavoriteIds()
    }

    override suspend fun toggleFavorite(photo: Photo) {
        return localPhotosDataSource.toggleFavorite(photo)
    }

    override fun observeIsFavorite(id: String): Flow<Boolean> {
        return localPhotosDataSource.observeIsFavorite(id)
    }
}
