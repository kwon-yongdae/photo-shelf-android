package com.yongdae.photoshelf.core.data.datasorce.local

import android.content.Context
import com.yongdae.photoshelf.core.data.datasorce.local.dao.FavoritePhotosDao
import com.yongdae.photoshelf.core.data.datasorce.local.entity.toEntity
import com.yongdae.photoshelf.core.data.datasorce.local.entity.toModel
import com.yongdae.photoshelf.core.domain.model.Photo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import javax.inject.Inject

class LocalPhotosDataSource @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val favoritePhotosDao: FavoritePhotosDao,
) {
    private val photosDir: File
        get() = File(context.filesDir, PHOTOS_DIR_NAME).apply { if (!exists()) mkdirs() }

    fun observeIsFavorite(): Flow<List<Photo>> {
        return favoritePhotosDao.observeAll().map { entities -> entities.map { it.toModel() } }
    }

    fun observeFavoriteIds(): Flow<Set<String>> {
        return favoritePhotosDao.observeFavoriteIds().map { ids -> ids.toSet() }
    }

    suspend fun addFavorite(photo: Photo, localPath: String? = null) {
        favoritePhotosDao.insert(photo.toEntity(localPath))
    }

    suspend fun removeFavorite(id: String) {
        favoritePhotosDao.deleteById(id)
    }

    suspend fun getPhoto(id: String): Photo? {
        return favoritePhotosDao.getById(id)?.toModel()
    }

    suspend fun getLocalPath(id: String): String? {
        return favoritePhotosDao.getLocalPath(id)
    }

    fun observeIsFavorite(id: String): Flow<Boolean> {
        return favoritePhotosDao.observeIsFavorite(id)
    }

    suspend fun savePhoto(id: String, byteArray: ByteArray): String = withContext(Dispatchers.IO) {
        val target = File(photosDir, id)
        val temp = File(photosDir, "${target.name}.tmp")

        try {
            temp.outputStream().use { it.write(byteArray) }
            if (target.exists()) target.delete()
            if (!temp.renameTo(target)) {
                throw IOException("Failed to move image file: ${target.absolutePath}")
            }
        } catch (e: Exception) {
            temp.delete()
            throw e
        }

        target.absolutePath
    }

    suspend fun deletePhoto(path: String?): Boolean = withContext(Dispatchers.IO) {
        if (path.isNullOrBlank()) return@withContext true
        val file = File(path)
        !file.exists() || file.delete()
    }

    companion object {
        private const val PHOTOS_DIR_NAME = "favorite_photos"
    }
}
