package com.yongdae.photoshelf.core.data.repository

import com.yongdae.photoshelf.core.data.datasorce.local.LocalPhotosDataSource
import com.yongdae.photoshelf.core.data.datasorce.remote.RemotePhotosDataSource
import com.yongdae.photoshelf.core.domain.model.Photo
import com.yongdae.photoshelf.core.domain.repository.PhotosRepository
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPhotosRepository @Inject constructor(
    private val localPhotosDataSource: LocalPhotosDataSource,
    private val remotePhotosDataSource: RemotePhotosDataSource,
) : PhotosRepository {

    private val favoriteMutex = Mutex()

    override suspend fun getPhotos(
        page: Int?,
        perPage: Int?
    ): List<Photo> {
        return remotePhotosDataSource.getPhotos(page, perPage)
    }

    override suspend fun getPhoto(
        id: String,
    ): Photo {
        return localPhotosDataSource.getPhoto(id) ?: remotePhotosDataSource.getPhoto(id)
    }

    override fun observeFavoritePhotos(): Flow<List<Photo>> {
        return localPhotosDataSource.observeIsFavorite()
    }

    override fun observeFavoriteIds(): Flow<Set<String>> {
        return localPhotosDataSource.observeFavoriteIds()
    }

    override suspend fun toggleFavorite(photo: Photo) = favoriteMutex.withLock {
        val existing = localPhotosDataSource.getPhoto(photo.id)
        if (existing == null) {
            addFavorite(photo)
        } else {
            removeFavorite(photo)
        }
    }

    private suspend fun addFavorite(photo: Photo) {
        val byteArray = remotePhotosDataSource.downloadPhoto(photo.id)
        val localPath = localPhotosDataSource.savePhoto(photo.id, byteArray)

        try {
            localPhotosDataSource.addFavorite(photo, localPath)
        } catch (e: Exception) {
            withContext(NonCancellable) { localPhotosDataSource.deletePhoto(localPath) }
            e.printStackTrace()
        }
    }

    private suspend fun removeFavorite(photo: Photo) {
        val localPath = localPhotosDataSource.getLocalPath(photo.id)
        localPhotosDataSource.removeFavorite(photo.id)

        try {
            withContext(NonCancellable) { localPhotosDataSource.deletePhoto(localPath) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observeIsFavorite(id: String): Flow<Boolean> {
        return localPhotosDataSource.observeIsFavorite(id)
    }
}
